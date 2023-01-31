package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PasswordEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PerfilEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Service
public class UsuarioEntityConverter {
	
	@Autowired
	private PerfilEntityConverter perfilEntityConverter;

	public Usuario converterParaUsuario(UsuarioEntity entity) {
		List<Perfil> perfis = entity.getPerfis().stream().map(perfilEntityConverter::converterParaPerfil).toList();
		return new Usuario(
				entity.getId(),
				entity.getUsername(),
				new Password(entity.getPassword()),
				perfis);
	}
	
	public UsuarioEntity converterParaUsuarioEntity(Usuario usuario) {
		List<PerfilEntity> perfis = usuario.getPerfis().stream().map(perfilEntityConverter::converterParaPerfilEntity).toList();
		return new UsuarioEntity(
				usuario.getId(),
				usuario.getUsername(),
				new PasswordEntity(usuario.getPassword().getSenha()),
				perfis);
	}
}
