package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscaDadosDePedidos {

	private final PedidoRepository repository;

	public BuscaDadosDePedidos(PedidoRepository repository) {
		this.repository = repository;
	}

	public DadosDePedido buscarPeloId(Long id) {
		BuscaPedidoPeloId buscaPedidoPeloId = new BuscaPedidoPeloId(repository);
		return new DadosDePedido(buscaPedidoPeloId.buscar(id));
	}

	public Page<DadosDePedido> buscarPelaComanda(Pageable paginacao, Comanda comanda) {
		return repository.buscarPelaComanda(paginacao, comanda).map(DadosDePedido::new);
	}

	public Page<DadosDePedido> buscarEntregues(Pageable paginacao, Boolean entregue) {
		return repository.buscarEntregue(paginacao, entregue).map(DadosDePedido::new);
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Page<DadosDePedido> buscarPorData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
		return repository.buscarPelaData(paginacao, dataInicial, dataFinal).map(DadosDePedido::new);
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Page<DadosDePedido> buscarTodos(Pageable paginacao) {
		return repository.buscarTodos(paginacao).map(DadosDePedido::new);
	}
}
