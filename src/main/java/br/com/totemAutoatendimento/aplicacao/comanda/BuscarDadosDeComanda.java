package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class BuscarDadosDeComanda {

    private ComandaRepository repository;

    public BuscarDadosDeComanda(ComandaRepository repository){
        this.repository = repository;
    }

    public DadosDeComanda executar(Long id){
        BuscarComanda buscarComanda = new BuscarComanda(repository);
        Comanda comanda = buscarComanda.executar(id);
        return new DadosDeComanda(comanda);
    }
}
