package br.com.totemAutoatendimento.dominio.pessoa.usuario;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public record DadosCriarUsuario(

		@NotBlank
		String nome,

		@NotBlank
		@Pattern(regexp = "\\d{11}")
		String cpf,

		@NotBlank
		@Pattern(regexp = "\\d{2,8}")
		String registro,

		@Email
		String email,

		@NotBlank
		String senha,

		@NotNull
		List<TipoPerfil> tipoPerfil

) {

}
