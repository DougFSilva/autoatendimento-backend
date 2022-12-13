package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscarPedido {
    
    private PedidoRepository repository;

    public BuscarPedido(PedidoRepository repository){
        this.repository = repository;
    }

    public Pedido executar(Long id){
        return repository.buscar(id).orElseThrow(() ->
            new ObjetoNaoEncontradoException("Pedido com id " + id + " não encontrado!"));
    }
}
