package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class RemoverUsuario {

	private UsuarioRepository repository;

	public RemoverUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public void executar(Long id) {
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		repository.remover(buscarUsuario.executar(id));
	}
}
