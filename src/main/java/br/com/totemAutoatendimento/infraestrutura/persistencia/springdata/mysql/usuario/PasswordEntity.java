package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.usuario;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.totemAutoatendimento.dominio.usuario.Password;
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
	
	public PasswordEntity(Password password) {
		this.senha = password.getSenha();
	}
}
