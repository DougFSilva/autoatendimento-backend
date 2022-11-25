package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

@Repository
public class UsuarioEntityRepository implements UsuarioRepository {
	
	@Autowired UsuarioEntityJpaRepository repository;

	@Override
	public Usuario criar(Usuario usuario) {
		return repository.save(new UsuarioEntity(usuario)).converterParaUsuario();
	}

	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Usuario editar(Long id, Usuario usuarioAtualizado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> buscar(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> buscarPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> buscarPorRegistro(String registro) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> buscarPorEmail(Email email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
