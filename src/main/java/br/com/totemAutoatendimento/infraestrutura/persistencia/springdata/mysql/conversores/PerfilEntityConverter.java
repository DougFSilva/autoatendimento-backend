package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PerfilEntity;

@Service
public class PerfilEntityConverter {

	public Perfil converterParaPerfil(PerfilEntity entity) {
		return new Perfil(entity.getTipo());
	}
	
	public PerfilEntity converterParaPerfilEntity(Perfil perfil) {
		return new PerfilEntity(null, perfil.getTipo());
	}
}
