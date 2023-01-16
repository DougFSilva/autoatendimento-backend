package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.comanda.BuscaComandaPeloId;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscaDadosDePedidos {

	private final PedidoRepository repository;
	
	private final ComandaRepository comandaRepository;

	public BuscaDadosDePedidos(PedidoRepository repository, ComandaRepository comandaRepository) {
		this.repository = repository;
		this.comandaRepository = comandaRepository;
	}

	public DadosDePedido buscarPeloId(Long id) {
		BuscaPedidoPeloId buscaPedidoPeloId = new BuscaPedidoPeloId(repository);
		return new DadosDePedido(buscaPedidoPeloId.buscar(id));
	}

	public List<DadosDePedido> buscarPelaComanda(Long comandaId) {
	BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(comandaRepository);
	Comanda comanda = buscaComandaPeloId.buscar(comandaId);
		return repository.buscarPelaComanda(comanda).stream().map(DadosDePedido::new).toList();
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
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
