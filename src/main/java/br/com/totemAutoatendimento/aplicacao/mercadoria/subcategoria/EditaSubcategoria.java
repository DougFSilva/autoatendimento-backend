package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class EditaSubcategoria {

    private final SubcategoriaRepository repository;

    public EditaSubcategoria(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    public Subcategoria editar(Subcategoria subcategoriaAtualizada) {
        BuscaSubcategoriaPeloId buscarSubcategoriaPeloId = new BuscaSubcategoriaPeloId(repository);
        Subcategoria subcategoria = buscarSubcategoriaPeloId.buscar(subcategoriaAtualizada.getId());
        Optional<Subcategoria> subcategoriaPorNome = repository
                .buscarPeloNome(subcategoriaAtualizada.getNome());
        if (subcategoriaPorNome.isPresent() && subcategoriaPorNome.get().getId() != subcategoriaAtualizada.getId()) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Subcategoria com nome " + subcategoriaAtualizada.getNome() + " já cadastrada!");
        }
        subcategoria.setNome(subcategoriaAtualizada.getNome());
        return repository.editar(subcategoria);
    }
}
