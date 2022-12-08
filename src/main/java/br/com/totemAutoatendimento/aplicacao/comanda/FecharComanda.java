package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;

public class FecharComanda {

    private ComandaRepository repository;

    public FecharComanda(ComandaRepository repository) {
        this.repository = repository;
    }

    public DadosDeComanda executar(DadosFecharComanda dados) {
        Optional<Comanda> comanda = repository.buscarComandaPorCartao(dados.cartao(), true);
        if (comanda.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Comanda com carta :" + dados.cartao() + " não encontrada!");
        }
        List<Pedido> pedidos = comanda.get().getPedidos();
        pedidos.forEach(pedido -> {
            if (!pedido.getEntregue()) {
                throw new RegrasDeNegocioException("Não é possível fechar comanda pois o pedido :"
                        + pedido.getMercadoria().getDescricao() + " não foi entregue!");
            }
        });
        comanda.get().set
    }
}
