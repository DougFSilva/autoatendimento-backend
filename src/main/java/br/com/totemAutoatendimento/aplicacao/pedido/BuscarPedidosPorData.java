package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscarPedidosPorData {
    
    private PedidoRepository repository;

    public BuscarPedidosPorData(PedidoRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Page<DadosDePedido> executar(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal){
       return repository.buscarPorData(paginacao, dataInicial, dataFinal).map(DadosDePedido::new);
    }
}
