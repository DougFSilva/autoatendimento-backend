package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class RemoveSubcategoria {

    private final SubcategoriaRepository repository;

    private final MercadoriaRepository mercadoriaRepository;

    public RemoveSubcategoria(SubcategoriaRepository repository, MercadoriaRepository mercadoriaRepository) {
        this.repository = repository;
        this.mercadoriaRepository = mercadoriaRepository;
    }
    
    public void remover(Long id) {
        BuscaSubcategoriaPeloId buscarSubcategoriaPeloId = new BuscaSubcategoriaPeloId(repository);
        Subcategoria subcategoria = buscarSubcategoriaPeloId.buscar(id);
        int quantidadeDeMercadoria = mercadoriaRepository.buscarPelaSubcategoria(Pageable.unpaged(), subcategoria)
                .getContent().size();
        if (quantidadeDeMercadoria > 0) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Impossível remover subcategoria pois existem mercadorias pertencentes a ela!");
        }
        repository.remover(subcategoria);
    }
}
