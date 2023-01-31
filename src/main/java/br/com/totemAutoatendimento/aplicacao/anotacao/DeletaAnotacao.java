package br.com.totemAutoatendimento.aplicacao.anotacao;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveAnotacao {

private final AnotacaoRepository repository;
	
	private final SystemLogger logger;
	
	public RemoveAnotacao(AnotacaoRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	public void remover(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Anotacao> anotacao = repository.buscarPeloId(id);
		if(anotacao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Anotacao com id %d não encontrada!", id));
		}
		logger.info(
				String.format("Usuario %s - Anotacao com id %d removida!", usuarioAutenticado.getRegistro(),anotacao.get().getId())
		);
		repository.remover(anotacao.get());
	}
}
