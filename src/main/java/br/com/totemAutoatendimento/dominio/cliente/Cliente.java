package br.com.totemAutoatendimento.dominio.cliente;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.Endereco;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Cliente {

	private Long id;
	
	private String nome;
	
	private String cpf;
	
	private String telefone;
	
	private Email email;
	
	private Endereco endereco;
	
	public Cliente(String nome, String cpf, String telefone, Email email, Endereco endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}
	
}
