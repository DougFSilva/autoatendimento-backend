package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import java.time.LocalTime;
import java.util.Optional;

import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class EntregarPedido {
    
    private PedidoRepository repository;

    public EntregarPedido(PedidoRepository repository) {
        this.repository = repository;
    }

    public void executar(Long id){
        Optional<Pedido> pedido = repository.buscar(id);
        if(pedido.isEmpty()){
            throw new ObjetoNaoEncontradoException("Pedido com id " + id + " não encontrado!");
        }
        pedido.get().setEntregue(true);
        pedido.get().setTempoEntrega(LocalTime.now());
        repository.editar(pedido.get());
    }
}
