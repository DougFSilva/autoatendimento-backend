package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscarComandaPorCartao {

    private ComandaRepository repository;

    public BuscarComandaPorCartao(ComandaRepository repository) {
        this.repository = repository;
    }

    public DadosDeComanda executar(String cartao) {
        Optional<Comanda> comanda = repository.buscarComandaPorCartao(cartao, true);
        if(comanda.isEmpty()){
            throw new ObjetoNaoEncontradoException("Comanda com carta :" + cartao + " não encontrada!");
        }
        return new DadosDeComanda(comanda.get());
    }
}
