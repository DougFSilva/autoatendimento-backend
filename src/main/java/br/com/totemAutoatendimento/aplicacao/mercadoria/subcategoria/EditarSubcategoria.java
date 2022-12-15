package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class EditarSubcategoria {

    private SubcategoriaRepository repository;

    public EditarSubcategoria(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Subcategoria executar(Subcategoria subcategoriaAtualizada) {
        BuscarSubcategoria buscarSubcategoria = new BuscarSubcategoria(repository);
        Subcategoria subcategoria = buscarSubcategoria.executar(subcategoriaAtualizada.getId());
        Optional<Subcategoria> subcategoriaPorNome = repository
                .buscarPorNome(subcategoriaAtualizada.getNome());
        if (subcategoriaPorNome.isPresent() && subcategoriaPorNome.get().getId() != subcategoriaAtualizada.getId()) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Subcategoria com nome " + subcategoriaAtualizada.getNome() + " já cadastrada!");
        }
        subcategoria.setNome(subcategoriaAtualizada.getNome());
        return repository.editar(subcategoria);
    }
}
