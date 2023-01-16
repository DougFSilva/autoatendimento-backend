package br.com.totemAutoatendimento.aplicacao.pedido;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscaPedidoPeloId {

	private final PedidoRepository repository;

	public BuscaPedidoPeloId(PedidoRepository repository) {
		this.repository = repository;
	}

	public Pedido buscar(Long id) {
		return repository.buscarPeloId(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Pedido com id " + id + " não encontrado!"));
	}
}
