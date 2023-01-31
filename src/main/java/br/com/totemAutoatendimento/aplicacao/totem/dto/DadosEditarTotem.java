package br.com.totemAutoatendimento.aplicacao.totem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public record DadosEditarTotem(

		@NotBlank
		@Pattern(regexp = "[a-zA-Z0-9]{2,8}")
		String identificador,
		
		@NotBlank
		String localDeInstalacao,
		
		@NotBlank
		@Pattern(regexp = "[a-zA-Z0-9]{6,12}")
		String username
		
		) {

}
