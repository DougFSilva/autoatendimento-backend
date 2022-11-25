package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.EmailEntity;

@Repository
public class UsuarioEntityRepository implements UsuarioRepository {
	
	@Autowired UsuarioEntityJpaRepository repository;

	@Override
	public Usuario criar(Usuario usuario) {
		return repository.save(new UsuarioEntity(usuario)).converterParaUsuario();
	}

	@Override
	public void remover(Usuario usuario) {
		repository.delete(new UsuarioEntity(usuario));
		
	}

	@Override
	public Usuario editar(Long id, Usuario usuarioAtualizado) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Usuario> buscar(Long id) {
		Optional<UsuarioEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaUsuario());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPorCpf(String cpf) {
		Optional<UsuarioEntity> entity = repository.findByCpf(cpf);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaUsuario());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPorRegistro(String registro) {
		Optional<UsuarioEntity> entity = repository.findByRegistro(registro);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaUsuario());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPorEmail(Email email) {
		Optional<UsuarioEntity> entity = repository.findByEmail(new EmailEntity(email.getEndereco()));
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaUsuario());
		}
		return Optional.empty();
	}
	
	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
