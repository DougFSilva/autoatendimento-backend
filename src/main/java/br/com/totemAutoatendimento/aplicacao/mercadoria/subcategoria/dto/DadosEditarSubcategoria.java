package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosEditarSubcategoria(@NotNull Long categoriaId, @NotBlank String nome) {

}
