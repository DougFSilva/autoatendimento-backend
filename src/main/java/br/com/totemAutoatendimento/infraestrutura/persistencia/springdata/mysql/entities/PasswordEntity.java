package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class PasswordEntity {

	@Column(name = "password")
	private String senha;
	
}
