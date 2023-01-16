package br.com.totemAutoatendimento.aplicacao.cliente.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.totemAutoatendimento.dominio.cliente.Estado;

public record DadosEditarCliente(
		
		@NotNull
		Long id,
		
		@NotBlank
		String nome, 
		
		@NotBlank
		@Pattern(regexp = "\\d{11}")
		String cpf, 
		
		@Pattern(regexp = "\\d{10,11}")
		String telefone, 
		
		@Email
		String email, 
		
		@NotNull
		Estado estado,
		
		@NotBlank
		String cidade, 
		
		@NotBlank
		String bairro, 
		
		@NotBlank
		String rua, 
		
		@NotBlank
		String numero
		) {

}
