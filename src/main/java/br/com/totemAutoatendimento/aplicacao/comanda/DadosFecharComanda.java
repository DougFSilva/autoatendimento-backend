package br.com.totemAutoatendimento.aplicacao.comanda;

import javax.validation.constraints.NotNull;

import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

public record DadosFecharComanda(

        @NotNull
        Long id,

        @NotNull 
        TipoPagamento tipoPagamento,

        @NotNull 
        Integer desconto

) {

}
