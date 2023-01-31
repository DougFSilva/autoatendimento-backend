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
	private UsuarioEntityDao dao;
	
	@Autowired
	private UsuarioEntityConverter usuarioEntityConverter;

	@Override
	public Usuario salvar(Usuario usuario) {
		UsuarioEntity entity = usuarioEntityConverter.converterParaUsuarioEntity(usuario);
		return usuarioEntityConverter.converterParaUsuario(dao.save(entity));
	}

	@Override
	public void deletar(Usuario usuario) {
		UsuarioEntity entity = usuarioEntityConverter.converterParaUsuarioEntity(usuario);
		dao.delete(entity);
	}

	@Override
	public Optional<Usuario> buscarPeloId(Long id) {
		Optional<UsuarioEntity> entity = dao.findById(id);
		if(entity.isPresent()) {
			return Optional.of(usuarioEntityConverter.converterParaUsuario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Usuario> buscarPeloUsername(String username) {
		Optional<UsuarioEntity> entity = dao.findByUsername(username);
		if(entity.isPresent()) {
			return Optional.of(usuarioEntityConverter.converterParaUsuario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Usuario> buscarTodos() {
		return dao.findAll().stream().map(usuarioEntityConverter::converterParaUsuario).toList();
	}
	
}
