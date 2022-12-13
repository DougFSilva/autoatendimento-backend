
package br.com.totemAutoatendimento.dominio.comanda;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import lombok.Getter;

@Getter
public enum TipoPagamento {

    CARTAO_CREDITO("Cartão de crédito"),
    CARTAO_DEBITO("Cartão de débito"),
    PIX("Pix"),
    DINHEIRO("Dinheiro"),
    NAO_PAGO("Não pago");

    private String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public static TipoPagamento toEnum(String tipo){
        for (TipoPagamento tipoPagamento: values()){
            if(tipoPagamento.name().equals(tipo)){
                return tipoPagamento;
            }
        }
        throw new ViolacaoDeIntegridadeDeDadosException("Tipo de pagamento " + tipo + " não conhecido!");
    }

}
