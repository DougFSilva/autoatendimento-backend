package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class BuscarComandaPorData {

    private ComandaRepository repository;

    public BuscarComandaPorData(ComandaRepository repository) {
        this.repository = repository;
    }

    public Page<DadosDeComanda> executar(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
        return repository.buscarPorData(paginacao, LocalDateTime.of(dataInicial, LocalTime.MIN),
                LocalDateTime.of(dataFinal, LocalTime.MAX)).map(DadosDeComanda::new);
    }
}
