package br.com.totemAutoatendimento.dominio.mercadoria;

import javax.validation.constraints.NotBlank;

public record DadosCriarSubcategoria (@NotBlank String nome) {
	
}