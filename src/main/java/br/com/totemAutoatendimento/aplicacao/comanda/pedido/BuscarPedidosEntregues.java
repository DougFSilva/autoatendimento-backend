package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;

public class BuscarPedidosEntregues {
    
    private PedidoRepository repository;

    public BuscarPedidosEntregues(PedidoRepository repository){
        this.repository = repository;
    }

    public Page<DadosDePedido> executar(Pageable paginacao, Boolean entregue){
       return repository.buscarPorEntregue(paginacao, entregue).map(DadosDePedido::new);
    }
}
