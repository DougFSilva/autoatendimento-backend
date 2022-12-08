package br.com.totemAutoatendimento.aplicacao.comanda;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

public record DadosFecharComanda(

        @NotBlank 
        String cartao,

        @NotNull 
        TipoPagamento tipoPagamento,

        @NotNull 
        Integer desconto

) {

}
