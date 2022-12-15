package br.com.totemAutoatendimento.aplicacao.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscarTodosPedidos {
    
    private PedidoRepository repository;

    public BuscarTodosPedidos(PedidoRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Page<DadosDePedido> executar(Pageable paginacao){
        return repository.buscarTodos(paginacao).map(DadosDePedido::new);
    }
}
