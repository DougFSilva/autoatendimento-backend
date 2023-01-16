package br.com.totemAutoatendimento.aplicacao.mercadoria.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosEditarMercadoria(

		@NotNull Long id,

		@NotBlank String codigo,

		@NotBlank Long idSubcategoria,

		@NotBlank String descricao,

		@NotNull BigDecimal preco,

		@NotNull Boolean promocao,

		@NotNull BigDecimal precoPromocional,

		@NotNull Boolean disponivel) {

}
