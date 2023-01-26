package br.com.totemAutoatendimento.aplicacao.anotacao;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosCriarOuEditarAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosDeAnotacao;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EditaAnotacao {

private final AnotacaoRepository repository;
	
	private final SystemLogger logger;
	
	public EditaAnotacao(AnotacaoRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	public DadosDeAnotacao editar(Long id, DadosCriarOuEditarAnotacao dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Anotacao> anotacao = repository.buscarPeloId(id);
		if(anotacao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Anotacao com id %d não encontrada!", id));
		}
		anotacao.get().setDescricao(dados.descricao());
		anotacao.get().setNivelDeImportancia(dados.nivelDeImportancia());
		Anotacao anotacaoEditada = repository.editar(anotacao.get());
		logger.info(
				String.format("Usuario %s - Anotacao com id %d editada!", usuarioAutenticado.getRegistro(),anotacao.get().getId())
		);
		return new DadosDeAnotacao(anotacaoEditada);
	}
}
