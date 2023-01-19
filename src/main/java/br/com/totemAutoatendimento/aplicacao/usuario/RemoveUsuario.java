package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class RemoveUsuario {

	private final UsuarioRepository repository;
	
	private final SystemLogger logger;

	public RemoveUsuario(UsuarioRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	@Transactional
	public void remover(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Usuario> usuario = repository.buscarPeloId(id);
		if(usuario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Usuário com id %d não encontrado!", id));
		}
		repository.remover(usuario.get());
		logger.info(String.format("Usuário %s -  Usuário com id %d removido!", usuarioAutenticado.getRegistro(), id));
	}
}
