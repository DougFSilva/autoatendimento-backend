package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.TotemEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Service
public class TotemEntityConverter {

	@Autowired
	private UsuarioEntityConverter usuarioEntityConverter;

	public Totem converterParaTotem(TotemEntity entity) {
		Usuario usuario = usuarioEntityConverter.converterParaUsuario(entity.getUsuario());
		return new Totem( 
				entity.getId(), 
				entity.getIdentificador(), 
				entity.getLocalDeInstalacao(),
				usuario);
	}
	
	public TotemEntity converterParaTotemEntity(Totem totem) {
		UsuarioEntity usuarioEntity = usuarioEntityConverter.converterParaUsuarioEntity(totem.getUsuario());
		return new TotemEntity( 
				totem.getId(), 
				totem.getIdentificador(), 
				totem.getLocalDeInstalacao(),
				usuarioEntity);
	}
}
