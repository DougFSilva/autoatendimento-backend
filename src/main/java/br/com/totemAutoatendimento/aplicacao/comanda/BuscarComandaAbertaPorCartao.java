package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscarComandaAbertaPorCartao {

    private ComandaRepository repository;

    public BuscarComandaAbertaPorCartao(ComandaRepository repository) {
        this.repository = repository;
    }

    public DadosDeComanda executar(String cartao) {
        Optional<Comanda> comanda = repository.buscarPorCartao(cartao, true);
        if(comanda.isEmpty()){
            throw new ObjetoNaoEncontradoException("Comanda com carta :" + cartao + " não encontrada!");
        }
        return new DadosDeComanda(comanda.get());
    }
}
