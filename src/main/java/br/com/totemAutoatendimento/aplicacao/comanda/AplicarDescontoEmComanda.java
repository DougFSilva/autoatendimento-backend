package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class AplicarDescontoEmComanda {
    
    private ComandaRepository repository;

    public AplicarDescontoEmComanda(ComandaRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public DadosDeComanda executar(Long id, Float desconto){
        BuscarComanda buscarComanda = new BuscarComanda(repository);
        Comanda comanda = buscarComanda.executar(id);
        comanda.aplicarDesconto(desconto);
        return new DadosDeComanda(repository.editar(comanda));
    }
}
