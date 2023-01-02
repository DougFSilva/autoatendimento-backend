package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class EntregarPedido {

    private PedidoRepository repository;

    public EntregarPedido(PedidoRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public void executar(Long id) {
        Optional<Pedido> pedido = repository.buscar(id);
        if (pedido.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Pedido com id " + id + " não encontrado!");
        }
        pedido.get().setEntregue(true);
        pedido.get().setTempoEntrega(LocalTime.now());
        repository.editar(pedido.get());
    }
}
