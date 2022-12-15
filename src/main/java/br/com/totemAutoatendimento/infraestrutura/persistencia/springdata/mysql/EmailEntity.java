package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.totemAutoatendimento.dominio.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class EmailEntity {

	@Column(name = "email")
	private String endereco;
	
	public EmailEntity(Email email) {
		this.endereco = email.getEndereco();
	}
	
}
