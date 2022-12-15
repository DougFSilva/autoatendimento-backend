package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class RemoverSubcategoria {

    private SubcategoriaRepository repository;

    private MercadoriaRepository mercadoriaRepository;

    public RemoverSubcategoria(SubcategoriaRepository repository, MercadoriaRepository mercadoriaRepository) {
        this.repository = repository;
        this.mercadoriaRepository = mercadoriaRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void executar(Long id) {
        BuscarSubcategoria buscarSubcategoria = new BuscarSubcategoria(repository);
        Subcategoria subcategoria = buscarSubcategoria.executar(id);
        int quantidadeDeMercadoria = mercadoriaRepository.buscarPorSubcategoria(Pageable.unpaged(), subcategoria)
                .getContent().size();
        if (quantidadeDeMercadoria > 0) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Impossível remover subcategoria pois existem " + quantidadeDeMercadoria
                            + " mercadorias pertencentes a ela!");
        }
        repository.remover(subcategoria);
    }
}
