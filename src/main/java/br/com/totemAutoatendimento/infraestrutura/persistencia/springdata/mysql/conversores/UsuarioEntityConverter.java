package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.EmailEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PasswordEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PerfilEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Service
public class UsuarioEntityConverter {

	@Autowired
	private PasswordEntityConverter passwordEntityConverter;

	@Autowired
	private EmailEntityConverter emailEntityConverter;
	
	@Autowired
	private PerfilEntityConverter perfilEntityConverter;

	public Usuario converterParaUsuario(UsuarioEntity entity) {
		Password password = passwordEntityConverter.converterParaPassword(entity.getPassword());
		Email email = emailEntityConverter.converterParaEmail(entity.getEmail());
		List<Perfil> perfis = entity.getPerfis().stream().map(perfil -> perfilEntityConverter.converterParaPerfil(perfil)).toList();
		return new Usuario(entity.getId(),
				entity.getNome(),
				entity.getCpf(),
				entity.getRegistro(),
				email,
				password,
				perfis);
	}

	public UsuarioEntity converterParaUsuarioEntity(Usuario usuario) {
		PasswordEntity passwordEntity = passwordEntityConverter.converterParaPasswordEntity(new Password(usuario.getPassword()));
		EmailEntity emailEntity = emailEntityConverter.converterParaEmailEntity(usuario.getEmail());
		List<PerfilEntity> perfis = usuario.getPerfis().stream().map(perfil -> perfilEntityConverter.converterParaPerfilEntity(perfil)).toList();
		return new UsuarioEntity(usuario.getId(),
				usuario.getNome(),
				usuario.getCpf(),
				usuario.getRegistro(),
				emailEntity,
				passwordEntity,
				perfis);
	}
}
