package br.com.totemAutoatendimento.infraestrutura.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.pedido.EventoDePedido;
import br.com.totemAutoatendimento.dominio.pedido.MensagemDePedido;

@Service
public class EnviarPedidoRecebidoWebsocket implements EventoDePedido {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Override
    public void notificar(MensagemDePedido mensagem) {
        messagingTemplate.convertAndSend("/topic/pedidos", mensagem);
    }

}
