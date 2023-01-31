package br.com.totemAutoatendimento.aplicacao.anotacao;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosCriarOuEditarAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosDeAnotacao;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EditaAnotacao {

	private final AnotacaoRepository repository;

	private final StandardLogger logger;

	public EditaAnotacao(AnotacaoRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public DadosDeAnotacao editar(Long id, DadosCriarOuEditarAnotacao dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Anotacao> anotacao = repository.buscarPeloId(id);
		if (anotacao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Anotacao com id %d não encontrada!", id));
		}
		anotacao.get().setDescricao(dados.descricao());
		anotacao.get().setNivelDeImportancia(dados.nivelDeImportancia());
		Anotacao anotacaoEditada = repository.salvar(anotacao.get());
		logger.info(String.format("Anotacao com id %d editada!", anotacao.get().getId()), usuarioAutenticado);
		return new DadosDeAnotacao(anotacaoEditada);
	}
}
