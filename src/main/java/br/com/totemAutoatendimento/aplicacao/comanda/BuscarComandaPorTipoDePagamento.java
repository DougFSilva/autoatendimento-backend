package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

public class BuscarComandaPorTipoDePagamento {

    private ComandaRepository repository;

    public BuscarComandaPorTipoDePagamento(ComandaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
    public Page<DadosDeComanda> executar(Pageable paginacao, TipoPagamento tipoPagamento){
        return repository.buscarPorTipoDePagamento(paginacao, tipoPagamento).map(DadosDeComanda::new);
    }
}
