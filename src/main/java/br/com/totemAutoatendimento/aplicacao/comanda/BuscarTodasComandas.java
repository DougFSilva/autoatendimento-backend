package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class BuscarTodasComandas {

    private ComandaRepository repository;

    public BuscarTodasComandas(ComandaRepository repository) {
        this.repository = repository;
    }

    public Page<DadosDeComanda> executar(Pageable paginacao){
        return repository.buscarTodas(paginacao).map(DadosDeComanda::new);
    }
}
