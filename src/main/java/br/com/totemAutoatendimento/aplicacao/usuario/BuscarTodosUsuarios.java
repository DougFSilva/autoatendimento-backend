package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class BuscarTodosUsuarios {

	private UsuarioRepository repository;

	public BuscarTodosUsuarios(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public List<DadosDeUsuario> executar(){
		return repository.buscarTodos().stream().map(DadosDeUsuario::new).toList();
	}
	
}
