package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.comanda.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class FazerPedido {

    private ComandaRepository repository;

    private MercadoriaRepository mercadoriaRepository;

    public FazerPedido(ComandaRepository repository,
            MercadoriaRepository mercadoriaRepository) {
        this.repository = repository;
        this.mercadoriaRepository = mercadoriaRepository;
    }

    public DadosDeComanda executar(DadosFazerPedido dados) {
        Optional<Comanda> comanda = repository.buscarPorCartao(dados.cartao(), true);
        if(comanda.isEmpty()){
            throw new ObjetoNaoEncontradoException("Comanda aberta com cartão " + dados.cartao() + " não encontrada!");
        }
        Optional<Mercadoria> mercadoria = mercadoriaRepository.buscarPorCodigo(dados.codigoDaMercadoria());
        if(mercadoria.isEmpty()){
            throw new ObjetoNaoEncontradoException("Mercadoria com código " + dados.codigoDaMercadoria() + " não encontrada!");
        }
        if(mercadoria.get().getQuantidade() < dados.quantidade()){
            throw new RegrasDeNegocioException("Não é possível realizar pedido. Quantidade de mercadorias indisponível!");
        }
        Pedido pedido = new Pedido(null, mercadoria.get(), dados.quantidade(), LocalDate.now(), LocalTime.now(), null, false);
        comanda.get().adicionarPedido(pedido);
        mercadoria.get().removerQuantidade(pedido.getQuantidade());
        mercadoriaRepository.editar(mercadoria.get());
        return new DadosDeComanda(repository.editar(comanda.get()));
    }

}
