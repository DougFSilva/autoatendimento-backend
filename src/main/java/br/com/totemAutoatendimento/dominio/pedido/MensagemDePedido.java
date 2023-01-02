package br.com.totemAutoatendimento.dominio.pedido;

import br.com.totemAutoatendimento.aplicacao.pedido.DadosDePedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class MensagemDePedido {
    
    private TipoDeMensagemDePedido tipoDeMensagemDePedido;

    private DadosDePedido pedido;
}
