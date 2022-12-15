package br.com.totemAutoatendimento.dominio.usuario;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Password {

	private String senha;
	
	public Password(String senha, CodificadorDeSenha codificador) {
		
		if(!senha.matches("^(.*[0-9]).*$")){
			throw new ViolacaoDeIntegridadeDeDadosException("A senha deve conter pelo menos um dígito!");
		}
		if(!senha.matches("^(?=.*[A-Z]).*$")){
			throw new ViolacaoDeIntegridadeDeDadosException("A senha deve conter pelo menos uma letra maiúscula!");
		}
		if(!senha.matches("^(?=.*[a-z]).*$")){
			throw new ViolacaoDeIntegridadeDeDadosException("A senha deve conter pelo menos uma letra minúscula!");
		}
		if(!senha.matches("^(?=.*[@#$%^&-+=().]).*$")){
			throw new ViolacaoDeIntegridadeDeDadosException("A senha deve conter pelo menos um caracter especial!");
		}
		if(!senha.matches("^(?=\\S+$).*$")){
			throw new ViolacaoDeIntegridadeDeDadosException("A senha náo deve conter espaços em branco!");
		}
		if(senha.length()<8 || senha.length()>20){
			throw new ViolacaoDeIntegridadeDeDadosException("A senha deve conter de 8 a 20 caracteres!");
		}
		
		this.senha = codificador.codificar(senha);
	}
}
