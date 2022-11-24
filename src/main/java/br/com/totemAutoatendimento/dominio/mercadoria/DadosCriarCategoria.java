package br.com.totemAutoatendimento.dominio.mercadoria;

import javax.validation.constraints.NotBlank;

public record DadosCriarCategoria(@NotBlank String nome) {

}
