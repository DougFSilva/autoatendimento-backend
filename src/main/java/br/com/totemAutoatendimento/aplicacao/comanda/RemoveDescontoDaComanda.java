package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class RemoveDescontoDaComanda {
    
    private final ComandaRepository repository;

    public RemoveDescontoDaComanda(ComandaRepository repository){
        this.repository = repository;
    }

    @Transactional
    public DadosDeComanda remover(Long id){
        BuscaComandaPeloId buscarComandaPeloId = new BuscaComandaPeloId(repository);
        Comanda comanda = buscarComandaPeloId.buscar(id);
        comanda.removerDesconto();
        return new DadosDeComanda(repository.editar(comanda));
    }
}
