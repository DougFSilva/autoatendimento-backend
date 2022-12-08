package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class BuscarComandaPorData {

    private ComandaRepository repository;

    public BuscarComandaPorData(ComandaRepository repository) {
        this.repository = repository;
    }

    public Page<DadosDeComanda> executar(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal){
        return repository.buscarPorData(paginacao, dataInicial, dataFinal).map(DadosDeComanda::new);
    }
}
