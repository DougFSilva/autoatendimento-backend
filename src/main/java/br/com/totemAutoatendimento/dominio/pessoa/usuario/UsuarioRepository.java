package br.com.totemAutoatendimento.dominio.pessoa.usuario;

import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.dominio.pessoa.Email;

public interface UsuarioRepository {

	Usuario criar(Usuario usuario);
	
	void remover(Long id);
	
	Usuario editar(Long id, Usuario usuarioAtualizado);
	
	Optional<Usuario> buscar(Long id);
	
	Optional<Usuario> buscarPorCpf(String cpf);
	
	Optional<Usuario> buscarPorRegistro(String registro);
	
	Optional<Usuario> buscarPorEmail(Email email);
	
	List<Usuario> buscarTodos();
	
}
