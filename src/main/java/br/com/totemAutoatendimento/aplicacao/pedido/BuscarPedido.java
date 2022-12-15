package br.com.totemAutoatendimento.aplicacao.pedido;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class BuscarPedido {
    
    private PedidoRepository repository;

    public BuscarPedido(PedidoRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Pedido executar(Long id){
        return repository.buscar(id).orElseThrow(() ->
            new ObjetoNaoEncontradoException("Pedido com id " + id + " não encontrado!"));
    }
}
