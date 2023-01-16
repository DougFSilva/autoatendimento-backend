package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class RemoveMercadoria {
    
    private final MercadoriaRepository repository;

    public RemoveMercadoria(MercadoriaRepository repository){
        this.repository = repository;
    }

    public void remover(Long id){
        BuscaMercadoriaPeloId buscarMercadoriaPeloId = new BuscaMercadoriaPeloId(repository);
        Mercadoria mercadoria = buscarMercadoriaPeloId.buscar(id);
        repository.remover(mercadoria);
    }
}
