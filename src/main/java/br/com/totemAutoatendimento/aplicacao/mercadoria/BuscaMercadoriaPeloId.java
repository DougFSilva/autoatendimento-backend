package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class BuscaMercadoriaPeloId {

    private final MercadoriaRepository repository;

    public BuscaMercadoriaPeloId(MercadoriaRepository repository) {
        this.repository = repository;
    }
    
    public Mercadoria buscar(Long id) {
        return repository.buscarPeloId(id)
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Mercadoria com id " + id + " não encontrada!"));
    }
}
