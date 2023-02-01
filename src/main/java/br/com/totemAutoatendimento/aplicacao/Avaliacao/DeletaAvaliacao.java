package br.com.totemAutoatendimento.aplicacao.Avaliacao;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.avaliacao.Avaliacao;
import br.com.totemAutoatendimento.dominio.avaliacao.AvaliacaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaAvaliacao {

	private final AvaliacaoRepository repository;
	
	private final StandardLogger logger;

	public DeletaAvaliacao(AvaliacaoRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Avaliacao> avaliacao = repository.buscarPeloId(id);
		if(avaliacao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Avaliação com id %d não encontrada!", id));
		}
		repository.deletar(avaliacao.get());
		logger.info(String.format("Avaliaçaõ com id %d deletada!", id), usuarioAutenticado);
	}
}
