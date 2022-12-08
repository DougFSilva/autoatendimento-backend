package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosEditarMercadoria(

@NotNull
Long id,

@NotBlank
String codigo,

@NotBlank
String categoria,

@NotBlank
String subcategoria,

@NotBlank
String descricao,

@NotNull
Integer quantidade,

@NotNull
BigDecimal preco,

@NotNull
Boolean promocao,

@NotNull
BigDecimal precoPromocional)
{

}
