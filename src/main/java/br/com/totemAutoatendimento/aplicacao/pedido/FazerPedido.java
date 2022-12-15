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
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

public class FazerPedido {

    private PedidoRepository repository;

    private ComandaRepository comandaRepository;

    private MercadoriaRepository mercadoriaRepository;

    public FazerPedido(PedidoRepository repository, ComandaRepository comandaRepository,
            MercadoriaRepository mercadoriaRepository) {
        this.repository = repository;
        this.comandaRepository = comandaRepository;
        this.mercadoriaRepository = mercadoriaRepository;
    }

    @Transactional
    public DadosDeComanda executar(String cartao, List<DadosFazerPedido> dados) {
        Optional<Comanda> comanda = comandaRepository.buscarPorCartao(cartao, true);
        if (comanda.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Comanda aberta com cartão " + cartao + " não encontrada!");
        }
        dados.forEach(dado -> {
            Optional<Mercadoria> mercadoria = mercadoriaRepository.buscarPorCodigo(dado.codigoDaMercadoria());
            if (mercadoria.isEmpty()) {
                throw new ObjetoNaoEncontradoException(
                        "Mercadoria com código " + dado.codigoDaMercadoria() + " não encontrada!");
            }
            if (!mercadoria.get().getDisponivel()) {
                throw new RegrasDeNegocioException(
                        "Não é possível realizar pedido, pois a mercadoria está indisponível!");
            }
            Pedido pedido = new Pedido(null, mercadoria.get(), dado.quantidade(), LocalDate.now(), LocalTime.now(),
                    null,
                    false);
            Pedido pedidoCriado = repository.criar(pedido);
            comanda.get().adicionarPedido(pedidoCriado);
        });
        return new DadosDeComanda(comandaRepository.editar(comanda.get()));
    }

}
