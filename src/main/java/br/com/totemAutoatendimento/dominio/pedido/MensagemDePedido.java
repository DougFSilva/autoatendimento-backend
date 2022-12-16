package br.com.totemAutoatendimento.dominio.pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MensagemDePedido {
    
    private TipoDeMensagemDePedido tipoDeMensagemDePedido;

    private Pedido pedido;
}
