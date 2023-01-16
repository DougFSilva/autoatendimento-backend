package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class RemoveComanda {

    private final ComandaRepository repository;

    public RemoveComanda(ComandaRepository repository){
        this.repository = repository;
    }
   
    @Transactional
    public void remover(Long id){
        BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
        Comanda comanda = buscaComandaPeloId.buscar(id);
        if(comanda.getAberta()){
            throw new RegrasDeNegocioException("Não é possível remover uma comanda aberta!");
        }
        repository.remover(comanda);
    }
}
