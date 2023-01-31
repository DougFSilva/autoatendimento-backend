package br.com.totemAutoatendimento.dominio.funcionario;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "cpf" })
@ToString
public class Funcionario {

	private Long id;

	private String matricula;

	private String nome;

	private String cpf;

	private Email email;
	
	private Usuario usuario;

}
