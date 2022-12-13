package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class RemoverDescontoDaComanda {
    
    private ComandaRepository repository;

    public RemoverDescontoDaComanda(ComandaRepository repository){
        this.repository = repository;
    }

    public DadosDeComanda executar(Long id){
        BuscarComanda buscarComanda = new BuscarComanda(repository);
        Comanda comanda = buscarComanda.executar(id);
        comanda.removerDesconto();
        return new DadosDeComanda(repository.editar(comanda));
    }
}
