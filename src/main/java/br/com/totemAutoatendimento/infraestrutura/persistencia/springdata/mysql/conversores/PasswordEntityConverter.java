package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PasswordEntity;

@Service
public class PasswordEntityConverter {

	public Password converterParaPassword(PasswordEntity entity) {
		return new Password(entity.getSenha());
	}

	public PasswordEntity converterParaPasswordEntity(Password password) {
		return new PasswordEntity(password.getSenha());
	}
}
