package br.com.totemAutoatendimento.aplicacao.mercadoria.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosEditarMercadoria(

		@NotBlank String codigo,

		@NotNull Long subcategoriaId,

		@NotBlank String descricao,

		@NotNull BigDecimal preco,

		@NotNull Boolean promocao,

		@NotNull BigDecimal precoPromocional,

		@NotNull Boolean disponivel) {

}
