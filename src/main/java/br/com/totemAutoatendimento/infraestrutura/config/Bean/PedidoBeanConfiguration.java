package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.pedido.BuscaDadosDePedidos;
import br.com.totemAutoatendimento.aplicacao.pedido.EntregaPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.FazPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.RemovePedido;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.ComandaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.MercadoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.PedidoEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.web.websocket.EnviarPedidoRecebidoWebsocket;

@Configuration
public class PedidoBeanConfiguration {

	@Autowired
	private PedidoEntityAdapter pedidoEntityAdapter;

	@Autowired
	private ComandaEntityAdapter comandaEntityAdapter;

	@Autowired
	private MercadoriaEntityAdapter mercadoriaEntityAdapter;

	@Autowired
	private EnviarPedidoRecebidoWebsocket enviarPedidoRecebidoWebsocket;

	@Bean
	public FazPedido fazPedido() {
		return new FazPedido(pedidoEntityAdapter, comandaEntityAdapter, mercadoriaEntityAdapter,
				enviarPedidoRecebidoWebsocket);
	}

	@Bean
	public RemovePedido removePedido() {
		return new RemovePedido(pedidoEntityAdapter, comandaEntityAdapter, enviarPedidoRecebidoWebsocket);
	}

	@Bean
	public EntregaPedido entregaPedido() {
		return new EntregaPedido(pedidoEntityAdapter);
	}

	@Bean
	public BuscaDadosDePedidos buscaDadosDePedidos() {
		return new BuscaDadosDePedidos(pedidoEntityAdapter, comandaEntityAdapter);
	}

}
