package br.com.totemAutoatendimento.dominio.pessoa.cliente;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.totemAutoatendimento.dominio.pessoa.Estado;

public record DadosCriarCliente(
		
		@NotBlank
		String nome, 
		
		@NotBlank
		@Size(min = 11, max = 11)
		Integer cpf, 
		
		@Size(min = 9)
		Integer telefone, 
		
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
