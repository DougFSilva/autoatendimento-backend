package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class BuscarMercadoria {

    private MercadoriaRepository repository;

    public BuscarMercadoria(MercadoriaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Mercadoria executar(Long id) {
        return repository.buscar(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Mercadoria com id " + id + " não encontrada!"));
    }
}
