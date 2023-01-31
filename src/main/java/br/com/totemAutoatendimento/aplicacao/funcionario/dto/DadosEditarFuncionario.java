package br.com.totemAutoatendimento.aplicacao.funcionario.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;

public record DadosEditarFuncionario(

		@NotBlank
		@Pattern(regexp = "[a-zA-Z0-9]{4,10}")
		String matricula,
		
		@NotBlank
		String nome,

		@NotBlank
		@Pattern(regexp = "\\d{11}")
		String cpf,

		@Email
		String email,
		
		@NotNull
		List<TipoPerfil> tipoPerfil
		
		
		) {

}
