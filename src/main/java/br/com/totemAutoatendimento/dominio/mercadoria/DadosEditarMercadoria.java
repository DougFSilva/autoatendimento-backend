package br.com.totemAutoatendimento.dominio.mercadoria;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosEditarMercadoria(

@NotNull
Long id,

@NotBlank
String categoria,

@NotBlank
String subcategoria,

@NotBlank
String descricao,

@NotBlank
Integer quantidade,

@NotBlank
BigDecimal preco,

@NotBlank
Boolean promocao,

@NotBlank
BigDecimal precoPromocional)
{

}
