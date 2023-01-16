package br.com.totemAutoatendimento.aplicacao.usuario.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;

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
