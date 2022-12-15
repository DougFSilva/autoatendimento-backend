package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class BuscarDadosDeUsuario {

	private UsuarioRepository repository;

	public BuscarDadosDeUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public DadosDeUsuario executar(Long id) {
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		return new DadosDeUsuario(buscarUsuario.executar(id));
	}
}
