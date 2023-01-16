package br.com.totemAutoatendimento.aplicacao.pedido.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosFazerPedido(

    @NotBlank
    String codigoDaMercadoria,
    
    @NotBlank
    String mesa,

    @NotNull
    Integer quantidade

)
{

}
