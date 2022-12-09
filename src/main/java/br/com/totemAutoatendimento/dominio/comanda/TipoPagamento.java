

package br.com.totemAutoatendimento.dominio.comanda;

import lombok.Getter;

@Getter
public enum TipoPagamento {

    CARTAO_CREDITO("Cartão de crédito"),
    CARTAO_DEBITO("Cartão de débito"),
    PIX("Pix"),
    DINHEIRO("Dinheiro"),
    NAO_PAGO("Não pago");

    private String descricao;

    TipoPagamento(String descricao){
        this.descricao = descricao;
    }


}
