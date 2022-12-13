package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;

public class BuscarTodosPedidos {
    
    private PedidoRepository repository;

    public BuscarTodosPedidos(PedidoRepository repository){
        this.repository = repository;
    }

    public Page<DadosDePedido> executar(Pageable paginacao){
        return repository.buscarTodos(paginacao).map(DadosDePedido::new);
    }
}
