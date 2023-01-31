package br.com.totemAutoatendimento.aplicacao.anotacao;

import java.time.LocalDateTime;

import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosCriarOuEditarAnotacao;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CriaAnotacao {

	private final AnotacaoRepository repository;

	private final StandardLogger logger;

	public CriaAnotacao(AnotacaoRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public Anotacao criar(DadosCriarOuEditarAnotacao dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Anotacao anotacao = new Anotacao(
				null, 
				LocalDateTime.now(), 
				usuarioAutenticado, 
				dados.descricao(),
				dados.nivelDeImportancia());
		Anotacao anotacaoCriada = repository.salvar(anotacao);
		logger.info(String.format("Anotacao com id %d criada!", anotacaoCriada.getId()), usuarioAutenticado);
		return anotacaoCriada;
	}
}
