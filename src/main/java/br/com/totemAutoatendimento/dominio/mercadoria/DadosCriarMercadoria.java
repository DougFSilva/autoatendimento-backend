package br.com.totemAutoatendimento.dominio.mercadoria;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

public record DadosCriarMercadoria(
		
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
		BigDecimal precoPromocional) {

}
