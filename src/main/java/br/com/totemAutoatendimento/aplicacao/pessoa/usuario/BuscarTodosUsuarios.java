package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import java.util.List;

import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class BuscarTodosUsuarios {

	private UsuarioRepository repository;

	public BuscarTodosUsuarios(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public List<DadosDeUsuario> executar(){
		return repository.buscarTodos().stream().map(DadosDeUsuario::new).toList();
	}
	
}
