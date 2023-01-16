package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.UsuarioEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.UsuarioEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Repository
public class UsuarioEntityAdapter implements UsuarioRepository {
	
	@Autowired 
	private UsuarioEntityDao repository;
	
	@Autowired
	private UsuarioEntityConverter usuarioEntityConverter;
	

	@Override
	public Usuario criar(Usuario usuario) {
		UsuarioEntity entity = repository.save(usuarioEntityConverter.converterParaUsuarioEntity(usuario));
		return usuarioEntityConverter.converterParaUsuario(entity);
	}

	@Override
	public void remover(Usuario usuario) {
		repository.delete(usuarioEntityConverter.converterParaUsuarioEntity(usuario));
	}

	@Override
	public Usuario editar(Usuario usuarioAtualizado) {
		UsuarioEntity entity = repository.save(usuarioEntityConverter.converterParaUsuarioEntity(usuarioAtualizado));
		return usuarioEntityConverter.converterParaUsuario(entity);
	}

	@Override
	public Optional<Usuario> buscarPeloId(Long id) {
		Optional<UsuarioEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return Optional.of(usuarioEntityConverter.converterParaUsuario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPeloCpf(String cpf) {
		Optional<UsuarioEntity> entity = repository.findByCpf(cpf);
		if(entity.isPresent()) {
			return Optional.of(usuarioEntityConverter.converterParaUsuario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPeloRegistro(String registro) {
		Optional<UsuarioEntity> entity = repository.findByRegistro(registro);
		if(entity.isPresent()) {
			return Optional.of(usuarioEntityConverter.converterParaUsuario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Usuario> buscarTodos() {
		return repository.findAll().stream().map(usuarioEntityConverter::converterParaUsuario).toList();
	}


}
