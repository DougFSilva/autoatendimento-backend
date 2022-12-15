package br.com.totemAutoatendimento.dominio.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

	Usuario criar(Usuario usuario);
	
	void remover(Usuario usuario);
	
	Usuario editar(Usuario usuarioAtualizado);
	
	Optional<Usuario> buscar(Long id);
	
	Optional<Usuario> buscarPorCpf(String cpf);
	
	Optional<Usuario> buscarPorRegistro(String registro);
	
	List<Usuario> buscarTodos();
	
}
