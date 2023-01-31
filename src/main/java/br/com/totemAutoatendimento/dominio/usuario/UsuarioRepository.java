package br.com.totemAutoatendimento.dominio.usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

	Usuario salvar(Usuario usuario);
	
	void deletar(Usuario usuario);
	
	Optional<Usuario> buscarPeloId(Long id);
	
	Optional<Usuario> buscarPeloUsername(String username);
	
	List<Usuario> buscarTodos();
}
