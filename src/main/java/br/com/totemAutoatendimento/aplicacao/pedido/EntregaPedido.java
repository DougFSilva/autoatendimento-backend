
package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalTime;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class EntregaPedido {

	private final PedidoRepository repository;

	public EntregaPedido(PedidoRepository repository) {
		this.repository = repository;
	}

	public void entregar(Long id) {
		BuscaPedidoPeloId buscaPedidoPeloId = new BuscaPedidoPeloId(repository);
		Pedido pedido = buscaPedidoPeloId.buscar(id);
		pedido.setEntregue(true);
		pedido.setTempoEntrega(LocalTime.now());
		repository.editar(pedido);
	}
}
