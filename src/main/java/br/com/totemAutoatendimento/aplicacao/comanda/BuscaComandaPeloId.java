package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscaComandaPeloId {

    private final ComandaRepository repository;

    public BuscaComandaPeloId(ComandaRepository repository){
        this.repository = repository;
    }

    public Comanda buscar(Long id){
       return repository.buscarPeloId(id).orElseThrow(() -> {
        throw new ObjetoNaoEncontradoException("Comanda com id " + id + " não encontrada!");
       });
    }
}
