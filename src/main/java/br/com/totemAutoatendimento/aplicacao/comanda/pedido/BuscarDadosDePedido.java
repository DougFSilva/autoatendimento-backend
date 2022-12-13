package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;

public class BuscarDadosDePedido {

    private PedidoRepository repository;

    public BuscarDadosDePedido(PedidoRepository repository) {
        this.repository = repository;
    }

    public DadosDePedido executar(Long id) {
        BuscarPedido buscarPedido = new BuscarPedido(repository);
        return new DadosDePedido(buscarPedido.executar(id));
    }
}
