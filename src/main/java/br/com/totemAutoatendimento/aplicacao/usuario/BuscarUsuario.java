package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class BuscarUsuario {

	private UsuarioRepository repository;

	public BuscarUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Usuario executar(Long id) {
		return repository.buscar(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário com id " + id + " não encontrado!"));
	}

}
