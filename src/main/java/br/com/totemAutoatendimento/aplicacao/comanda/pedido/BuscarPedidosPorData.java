package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;

public class BuscarPedidosPorData {
    
    private PedidoRepository repository;

    public BuscarPedidosPorData(PedidoRepository repository){
        this.repository = repository;
    }

    public Page<DadosDePedido> executar(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal){
       return repository.buscarPorData(paginacao, dataInicial, dataFinal).map(DadosDePedido::new);
    }
}
