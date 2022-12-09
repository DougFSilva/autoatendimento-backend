package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosFazerPedido(

    @NotNull
    String cartao,

    @NotBlank
    String codigoDaMercadoria,

    @NotNull
    Integer quantidade

)
{

}
