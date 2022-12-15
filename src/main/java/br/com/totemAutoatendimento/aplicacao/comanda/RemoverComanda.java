package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;

public class RemoverComanda {

    private ComandaRepository repository;

    public RemoverComanda(ComandaRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    @Transactional
    public void executar(Long id){
        BuscarComanda buscarComanda = new BuscarComanda(repository);
        Comanda comanda = buscarComanda.executar(id);
        if(comanda.getAberta()){
            throw new RegrasDeNegocioException("Não é possível remover uma comanda aberta!");
        }
        repository.remover(comanda);
    }
}
