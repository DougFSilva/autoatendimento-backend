package br.com.totemAutoatendimento.aplicacao.anotacao.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.totemAutoatendimento.dominio.anotacao.NivelDeImportancia;

public record DadosCriarOuEditarAnotacao(

		@NotBlank String descricao,

		@NotNull NivelDeImportancia nivelDeImportancia) {

}
