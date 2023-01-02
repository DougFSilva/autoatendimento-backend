package br.com.totemAutoatendimento.aplicacao.pedido;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.pedido.EventoDePedido;
import br.com.totemAutoatendimento.dominio.pedido.MensagemDePedido;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.pedido.TipoDeMensagemDePedido;

public class RemoverPedido {

    private PedidoRepository repository;

    private ComandaRepository comandaRepository;

    private EventoDePedido eventoDePedido;

    public RemoverPedido(PedidoRepository repository, ComandaRepository comandaRepository,
            EventoDePedido eventoDePedido) {
        this.repository = repository;
        this.comandaRepository = comandaRepository;
        this.eventoDePedido = eventoDePedido;
    }

    @Transactional
    public DadosDeComanda executar(DadosRemoverPedido dados) {
        Optional<Comanda> comanda = comandaRepository.buscarPorCartao(dados.cartao(), true);
        if (comanda.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Comanda aberta com cartão " + dados.cartao() + " não encontrada!");
        }
        Optional<Pedido> pedido = repository.buscar(dados.id());
        if (pedido.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Pedido com id " + dados.id() + " não encontrado!");
        }
        if (pedido.get().getEntregue()) {
            throw new RegrasDeNegocioException("Não é possível cancelar pedido que já foi entregue!");
        }
        comanda.get().removerPedido(pedido.get());
        Comanda comandaAtualizada = comandaRepository.editar(comanda.get());
        repository.remover(pedido.get());
        eventoDePedido.notificar(
                new MensagemDePedido(TipoDeMensagemDePedido.PEDIDO_REMOVIDO, new DadosDePedido(pedido.get())));
        return new DadosDeComanda(comandaAtualizada);
    }
}
