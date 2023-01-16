package br.com.totemAutoatendimento.aplicacao.pedido.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosRemoverPedido(

    @NotNull
    Long id,

    @NotBlank
    String cartao
) {
    
}
