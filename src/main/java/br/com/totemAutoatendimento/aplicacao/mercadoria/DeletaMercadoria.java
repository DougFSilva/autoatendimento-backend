package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaMercadoria {

	private final MercadoriaRepository repository;

	private final StandardLogger logger;

	public DeletaMercadoria(MercadoriaRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Mercadoria> mercadoria = repository.buscarPeloId(id);
		if (mercadoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Mercadoria com id %d não encontrada!", id));
		}
		repository.deletar(mercadoria.get());
		logger.info(String.format("Mercadoria com código %s deletada!", mercadoria.get().getCodigo()), usuarioAutenticado);
	}
}
