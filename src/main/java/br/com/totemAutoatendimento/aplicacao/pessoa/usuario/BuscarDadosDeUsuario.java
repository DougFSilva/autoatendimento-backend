package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class BuscarDadosDeUsuario {

	private UsuarioRepository repository;

	public BuscarDadosDeUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public DadosDeUsuario executar(Long id) {
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		return new DadosDeUsuario(buscarUsuario.executar(id));
	}
}
