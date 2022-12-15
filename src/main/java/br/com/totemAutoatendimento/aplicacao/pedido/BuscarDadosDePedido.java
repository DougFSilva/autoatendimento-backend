package br.com.totemAutoatendimento.aplicacao.pedido;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscarDadosDePedido {

    private PedidoRepository repository;

    public BuscarDadosDePedido(PedidoRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public DadosDePedido executar(Long id) {
        BuscarPedido buscarPedido = new BuscarPedido(repository);
        return new DadosDePedido(buscarPedido.executar(id));
    }
}
