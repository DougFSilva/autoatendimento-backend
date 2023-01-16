package br.com.totemAutoatendimento.dominio.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

	Usuario criar(Usuario usuario);
	
	void remover(Usuario usuario);
	
	Usuario editar(Usuario usuarioAtualizado);
	
	Optional<Usuario> buscarPeloId(Long id);
	
	Optional<Usuario> buscarPeloCpf(String cpf);
	
	Optional<Usuario> buscarPeloRegistro(String registro);
	
	List<Usuario> buscarTodos();
	
}
