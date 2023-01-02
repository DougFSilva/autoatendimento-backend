package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.pedido.EventoDePedido;
import br.com.totemAutoatendimento.dominio.pedido.MensagemDePedido;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.pedido.TipoDeMensagemDePedido;

public class FazerPedido {

    private PedidoRepository repository;

    private ComandaRepository comandaRepository;

    private MercadoriaRepository mercadoriaRepository;

    private EventoDePedido eventoDePedido;

    public FazerPedido(PedidoRepository repository, ComandaRepository comandaRepository,
            MercadoriaRepository mercadoriaRepository, EventoDePedido eventoDePedido) {
        this.repository = repository;
        this.comandaRepository = comandaRepository;
        this.mercadoriaRepository = mercadoriaRepository;
        this.eventoDePedido = eventoDePedido;
    }

    @Transactional
    public DadosDeComanda executar(String cartao, List<DadosFazerPedido> dados) {
        Optional<Comanda> comanda = comandaRepository.buscarPorCartao(cartao, true);
        if (comanda.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Comanda aberta com cartão " + cartao + " não encontrada!");
        }
        VerificarDisponibilidadeDeMercadoria verificarDisponilidade = new VerificarDisponibilidadeDeMercadoria(mercadoriaRepository);
        dados.forEach(dado -> {
            Mercadoria mercadoria = verificarDisponilidade.executar(dado.codigoDaMercadoria());
            Pedido pedido = new Pedido(null, mercadoria, dado.quantidade(), LocalDate.now(), LocalTime.now(),
                    null,
                    false);
            Pedido pedidoCriado = repository.criar(pedido);
            comanda.get().adicionarPedido(pedidoCriado);
            eventoDePedido.notificar(new MensagemDePedido(TipoDeMensagemDePedido.PEDIDO_EFETUADO, new DadosDePedido(pedidoCriado)));
        });
        return new DadosDeComanda(comandaRepository.editar(comanda.get()));
    }

}
