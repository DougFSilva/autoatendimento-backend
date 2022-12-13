package br.com.totemAutoatendimento.dominio.comanda;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PedidoRepository {

    Pedido criar(Pedido pedido);

    void remover(Pedido pedido);

    Pedido editar(Pedido pedidoAtualizado);

    Optional<Pedido> buscar(Long id);

    Page<Pedido> buscarPorData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);

    Page<Pedido> buscarPorEntregue(Pageable paginacao, Boolean entregue);

    Page<Pedido> buscarTodos(Pageable paginacao);
}
