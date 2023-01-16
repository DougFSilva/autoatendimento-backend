package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.EmailEntity;

@Service
public class EmailEntityConverter {

	public Email converterParaEmail(EmailEntity entity) {
		return new Email(entity.getEndereco());
	}

	public EmailEntity converterParaEmailEntity(Email email) {
		return new EmailEntity(email.getEndereco());
	}
}
