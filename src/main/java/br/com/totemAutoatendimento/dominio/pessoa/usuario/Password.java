package br.com.totemAutoatendimento.dominio.pessoa.usuario;

import br.com.totemAutoatendimento.dominio.exception.SenhaInvalidaException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Password {

	private String senha;
	
	public Password(String senha, CodificadorDeSenha codificador) {
		
		if(!senha.matches("^(.*[0-9]).*$")){
			throw new SenhaInvalidaException("A senha deve conter pelo menos um dígito!");
		}
		if(!senha.matches("^(?=.*[A-Z]).*$")){
			throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra maiúscula!");
		}
		if(!senha.matches("^(?=.*[a-z]).*$")){
			throw new SenhaInvalidaException("A senha deve conter pelo menos uma letra minúscula!");
		}
		if(!senha.matches("^(?=.*[@#$%^&-+=().]).*$")){
			throw new SenhaInvalidaException("A senha deve conter pelo menos um caracter especial!");
		}
		if(!senha.matches("^(?=\\S+$).*$")){
			throw new SenhaInvalidaException("A senha náo deve conter espaços em branco!");
		}
		if(senha.length()<8 || senha.length()>20){
			throw new SenhaInvalidaException("A senha deve conter de 8 a 20 caracteres!");
		}
		
		this.senha = codificador.codificar(senha);
	}
}
