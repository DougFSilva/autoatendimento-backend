package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaCategoriaPeloId;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosDeSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosEditarSubcategoria;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class EditaSubcategoria {

    private final SubcategoriaRepository repository;
    
    private final CategoriaRepository categoriaRepository;

    public EditaSubcategoria(SubcategoriaRepository repository, CategoriaRepository categoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public DadosDeSubcategoria editar(Long id, DadosEditarSubcategoria dados) {
        BuscaSubcategoriaPeloId buscarSubcategoriaPeloId = new BuscaSubcategoriaPeloId(repository);
        Subcategoria subcategoria = buscarSubcategoriaPeloId.buscar(id);
        Optional<Subcategoria> subcategoriaPorNome = repository.buscarPeloNome(dados.nome());
        if (subcategoriaPorNome.isPresent() && subcategoriaPorNome.get().getId() != id) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Subcategoria com nome " + dados.nome() + " já cadastrada!");
        }
        BuscaCategoriaPeloId buscaCategoriaPeloId = new BuscaCategoriaPeloId(categoriaRepository);
        Categoria categoria = buscaCategoriaPeloId.buscar(dados.categoriaId());
        subcategoria.setNome(dados.nome());
        subcategoria.setCategoria(categoria);
        return new DadosDeSubcategoria(repository.editar(subcategoria));
    }
}
