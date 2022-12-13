package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;

@Repository
public class PedidoEntityRepository implements PedidoRepository {

    @Autowired
    private PedidoEntityJpaRepository repository;

    @Override
    public Pedido criar(Pedido pedido) {
        return repository.save(new PedidoEntity(pedido)).converterParaPedido();
    }

    @Override
    public void remover(Pedido pedido) {
        repository.delete(new PedidoEntity(pedido));
    }

    @Override
    public Pedido editar(Pedido pedidoAtualizado) {
        return repository.save(new PedidoEntity(pedidoAtualizado)).converterParaPedido();
    }

    @Override
    public Optional<Pedido> buscar(Long id) {
        Optional<PedidoEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            return Optional.of(entity.get().converterParaPedido());
        }
        return Optional.empty();
    }

    @Override
    public Page<Pedido> buscarTodos(Pageable paginacao) {
        return repository.findAll(paginacao).map(PedidoEntity::converterParaPedido);
    }

    @Override
    public Page<Pedido> buscarPorData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
        return repository.findAllByDataBetween(paginacao, dataInicial, dataFinal)
                .map(PedidoEntity::converterParaPedido);
    }

    @Override
    public Page<Pedido> buscarPorEntregue(Pageable paginacao, Boolean entregue) {
        return repository.findAllByEntregue(paginacao, entregue).map(PedidoEntity::converterParaPedido);
    }


}
