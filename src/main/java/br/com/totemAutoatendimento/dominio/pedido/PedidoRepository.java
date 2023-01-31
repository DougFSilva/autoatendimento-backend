package br.com.totemAutoatendimento.dominio.pedido;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;

public interface PedidoRepository {

	Pedido salvar(Pedido pedido);

	void deletar(Pedido pedido);

	Optional<Pedido> buscarPeloId(Long id);

	List<Pedido> buscarPelaComanda(Comanda comanda);

	Page<Pedido> buscarPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);

	Page<Pedido> buscarEntregue(Pageable paginacao, Boolean entregue);

	Page<Pedido> buscarTodos(Pageable paginacao);
}
