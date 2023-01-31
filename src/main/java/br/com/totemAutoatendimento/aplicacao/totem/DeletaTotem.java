package br.com.totemAutoatendimento.aplicacao.totem;

import java.util.Optional;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.totem.TotemRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaTotem {

	private final TotemRepository repository;

	private final StandardLogger logger;

	public DeletaTotem(TotemRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Totem> totem = repository.buscarPeloId(id);
		if (totem.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Totem com id %d não encotrado!", id));
		}
		repository.deletar(totem.get());
		logger.info(String.format("Totem com identificado %s deletado!", totem.get().getIdentificador()),
				usuarioAutenticado);
	}
}
