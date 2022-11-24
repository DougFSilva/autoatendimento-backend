package br.com.totemAutoatendimento.dominio.pessoa.usuario;

import java.util.List;

public interface UsuarioRepository {

	Usuario criar(Usuario usuario);
	
	void remover(Long id);
	
	Usuario editar(Long id, Usuario usuarioAtualizado);
	
	Usuario buscar(Long id);
	
	List<Usuario> buscarTodos();
	
}
