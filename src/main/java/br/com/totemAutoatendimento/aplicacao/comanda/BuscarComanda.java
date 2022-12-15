package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscarComanda {

    private ComandaRepository repository;

    public BuscarComanda(ComandaRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Comanda executar(Long id){
       return repository.buscar(id).orElseThrow(() -> {
        throw new ObjetoNaoEncontradoException("Comanda com id " + id + " não encontrada!");
       });
    }
}
