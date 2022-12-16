package br.com.totemAutoatendimento.dominio.pedido;

public interface EventoDePedido {
    
    void notificar(MensagemDePedido mensagem);
}
