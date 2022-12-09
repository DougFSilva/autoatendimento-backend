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
public class PedidoEntityRepository implements PedidoRepository{
    
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
    public Optional<Pedido> buscar(Long id) {
        Optional<PedidoEntity> entity = repository.findById(id);
        if(entity.isPresent()){
            return Optional.of(entity.get().converterParaPedido());
        }
        return Optional.empty();
    }

    @Override
    public Page<Pedido> buscarTodos(Pageable paginacao) {
        return repository.findAll(paginacao).map(PedidoEntity::converterParaPedido);
    }

    @Override
    public Page<Pedido> buscarPorData(Pageable paginacao, LocalDate data) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Pedido> buscarPorEntregue(Pageable paginacao, Boolean entregue) {
        // TODO Auto-generated method stub
        return null;
    }

}
