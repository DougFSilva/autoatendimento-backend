package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaComanda {

	private final ComandaRepository repository;

	private final StandardLogger logger;

	public DeletaComanda(ComandaRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Comanda> comanda = repository.buscarPeloId(id);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Comanda com id %d não encontrada!", id));
		}
		if (comanda.get().getAberta()) {
			throw new RegrasDeNegocioException("Não é possível remover uma comanda aberta!");
		}
		repository.deletar(comanda.get());
		logger.info(String.format("Comanda com id %d deletada!", comanda.get().getId()), usuarioAutenticado);
	}
}
