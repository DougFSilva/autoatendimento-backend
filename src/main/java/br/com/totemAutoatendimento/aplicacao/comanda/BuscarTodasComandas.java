package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class BuscarTodasComandas {

    private ComandaRepository repository;

    public BuscarTodasComandas(ComandaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Page<DadosDeComanda> executar(Pageable paginacao){
        return repository.buscarTodas(paginacao).map(DadosDeComanda::new);
    }
}
