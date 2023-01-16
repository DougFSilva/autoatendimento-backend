package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

@PreAuthorize("hasRole('ADMIN')")
public class RemoveUsuario {

	private UsuarioRepository repository;

	public RemoveUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public void remover(Long id) {
		BuscaUsuarioPeloId buscaUsuarioPeloId = new BuscaUsuarioPeloId(repository);
		repository.remover(buscaUsuarioPeloId.buscar(id));
	}
}
