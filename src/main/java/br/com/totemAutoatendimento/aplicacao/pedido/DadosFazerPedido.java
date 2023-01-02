package br.com.totemAutoatendimento.aplicacao.pedido;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosFazerPedido(

    @NotBlank
    String codigoDaMercadoria,

    @NotNull
    Integer quantidade

)
{

}
