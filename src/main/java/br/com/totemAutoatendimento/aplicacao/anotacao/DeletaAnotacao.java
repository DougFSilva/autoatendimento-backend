package br.com.totemAutoatendimento.aplicacao.anotacao;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaAnotacao {

	private final AnotacaoRepository repository;

	private final StandardLogger logger;

	public DeletaAnotacao(AnotacaoRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Anotacao> anotacao = repository.buscarPeloId(id);
		if (anotacao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Anotacao com id %d não encontrada!", id));
		}
		logger.info(String.format("Anotacao com id %d deletada!", anotacao.get().getId()), usuarioAutenticado);
		repository.deletar(anotacao.get());
	}
}
