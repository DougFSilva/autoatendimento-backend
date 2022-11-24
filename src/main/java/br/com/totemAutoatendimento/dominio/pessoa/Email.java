package br.com.totemAutoatendimento.dominio.pessoa;

import br.com.totemAutoatendimento.dominio.exception.EmailInvalidoException;
import lombok.Getter;

@Getter
public class Email {

	private String endereco;
	
	public Email(String endereco) {
		if (endereco == null || !endereco.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

			throw new EmailInvalidoException("Email " + endereco + " não é valido!");
		}
		this.endereco = endereco;
	}
}
