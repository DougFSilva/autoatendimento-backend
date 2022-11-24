package br.com.totemAutoatendimento.dominio.pessoa.cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import br.com.totemAutoatendimento.dominio.pessoa.Estado;

public record DadosCriarCliente(
		
		@NotBlank
		String nome, 
		
		@NotBlank
		@Pattern(regexp = "\\d{11}")
		String cpf, 
		
		@Pattern(regexp = "\\d{10,11}")
		String telefone, 
		
		@Email
		String enderecoEmail, 
		
		@NotBlank
		Estado estado,
		
		@NotBlank
		String cidade, 
		
		@NotBlank
		String bairro, 
		
		@NotBlank
		String rua, 
		
		@NotBlank
		String numero) {

}
