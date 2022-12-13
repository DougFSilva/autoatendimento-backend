package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.comanda.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.comanda.PedidoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;

public class RemoverPedido {  

    private PedidoRepository repository;
    
    private ComandaRepository comandaRepository;

    public RemoverPedido(PedidoRepository repository, ComandaRepository comandaRepository) {
        this.repository = repository;
        this.comandaRepository = comandaRepository;
    }

    public DadosDeComanda executar(Long id) {
        BuscarPedido buscarPedido = new BuscarPedido(repository);
        Pedido pedido = buscarPedido.executar(id);
        Optional<Comanda> comanda = comandaRepository.buscarPorPedido(pedido);
        if(comanda.isEmpty()){
            throw new ObjetoNaoEncontradoException("Comanda para pedido com id " + id + " não encontrada!");
        }
        if(!comanda.get().getAberta()){
            throw new RegrasDeNegocioException("Não é possível remover pedido pois a comanda já foi fechada!");
        }
        comanda.get().removerPedido(pedido);
        return new DadosDeComanda(comandaRepository.editar(comanda.get()));
    }
}
