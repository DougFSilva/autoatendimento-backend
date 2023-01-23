package br.com.totemAutoatendimento.dominio;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import lombok.Getter;

@Getter
public class Email {

	private String endereco;
	
	public Email(String endereco) {
		if (endereco == null || !endereco.matches("^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {

			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Email %s não é valido!", endereco));
		}
		this.endereco = endereco;
	}
}
