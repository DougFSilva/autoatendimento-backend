package br.com.totemAutoatendimento.aplicacao.Avaliacao;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.Avaliacao.dto.DadosCriarAvaliacao;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.avaliacao.Avaliacao;
import br.com.totemAutoatendimento.dominio.avaliacao.AvaliacaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.totem.TotemRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CriaAvaliacao {

	private final AvaliacaoRepository repository;

	private final TotemRepository totemRepository;

	public CriaAvaliacao(AvaliacaoRepository repository, TotemRepository totemRepository) {
		this.repository = repository;
		this.totemRepository = totemRepository;
	}

	public Avaliacao criar(DadosCriarAvaliacao dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilTotem(usuarioAutenticado);
		Optional<Totem> totem = totemRepository.buscarPeloUsuario(usuarioAutenticado.getId());
		if (totem.isEmpty()) {
			throw new ObjetoNaoEncontradoException(
					String.format("Totem com id %d não encontrado!", usuarioAutenticado.getId()));
		}
		Optional<Avaliacao> ultimaAvaliacao = repository.buscarUltimaPeloTotem(totem.get().getId());
		if (ultimaAvaliacao.isPresent()) {
			long diferencaDeTempoEmSegundos = LocalDateTime.now().until(ultimaAvaliacao.get().getTimestamp(), ChronoUnit.SECONDS);
			if (diferencaDeTempoEmSegundos < Avaliacao.tempoEntreAvaliacoes) {
				throw new RegrasDeNegocioException(String
						.format("Somente é possível realizar avaliações a cada %d segundos para cada totem", Avaliacao.tempoEntreAvaliacoes));
			}
		}
		Avaliacao avaliacao = new Avaliacao(
				null, 
				LocalDateTime.now(), 
				totem.get(), 
				dados.QualidadeDeMercadorias(),
				dados.OpcoesDeMercadorias(),
				dados.tempoDeAtendimento(), 
				dados.ambiente(),
				dados.experienciaComAutoatendimento(), 
				dados.funcionarios(), 
				dados.gerencia());
		Avaliacao avaliacaoCriada = repository.salvar(avaliacao);
		return avaliacaoCriada;
	}

}
