package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class BuscarComandasAbertas {

    private ComandaRepository repository;

    public BuscarComandasAbertas(ComandaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Page<DadosDeComanda> executar(Pageable paginacao, Boolean aberta){
        return repository.buscarAbertas(paginacao, aberta).map(DadosDeComanda::new);
    }
}
