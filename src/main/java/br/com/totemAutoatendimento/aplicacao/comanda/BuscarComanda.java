package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscarComanda {

    private ComandaRepository repository;

    public BuscarComanda(ComandaRepository repository){
        this.repository = repository;
    }

    public Comanda executar(Long id){
       return repository.buscar(id).orElseThrow(() -> {
        throw new ObjetoNaoEncontradoException("Comanda com id " + id + " não encontrada!");
       });
    }
}
