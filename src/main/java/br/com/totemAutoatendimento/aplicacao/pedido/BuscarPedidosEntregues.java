package br.com.totemAutoatendimento.aplicacao.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscarPedidosEntregues {
    
    private PedidoRepository repository;

    public BuscarPedidosEntregues(PedidoRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Page<DadosDePedido> executar(Pageable paginacao, Boolean entregue){
       return repository.buscarPorEntregue(paginacao, entregue).map(DadosDePedido::new);
    }
}
