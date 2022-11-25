package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class RemoverUsuario {

	private UsuarioRepository repository;

	public RemoverUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public void executar(Long id) {
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		repository.remover(buscarUsuario.executar(id));
	}
}
