package br.com.totemAutoatendimento.aplicacao.Avaliacao;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.Avaliacao.dto.DadosDeAvaliacao;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.avaliacao.Avaliacao;
import br.com.totemAutoatendimento.dominio.avaliacao.AvaliacaoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDeAvaliacao {

	private final AvaliacaoRepository repository;

	public BuscaDadosDeAvaliacao(AvaliacaoRepository repository) {
		this.repository = repository;
	}

	public Page<DadosDeAvaliacao> buscarTodasPelaData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal,
			Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Page<Avaliacao> avaliacoes = repository.buscarTodasPelaData(paginacao, dataInicial, dataFinal);
		return avaliacoes.map(DadosDeAvaliacao::new);
	}
	
	public Page<DadosDeAvaliacao> buscarTodas(Pageable paginacao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		return repository.buscarTodas(paginacao).map(DadosDeAvaliacao::new);
	}
	
}
