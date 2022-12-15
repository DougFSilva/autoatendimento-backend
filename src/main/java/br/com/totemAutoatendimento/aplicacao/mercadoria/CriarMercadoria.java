package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarCategoriaPorNome;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscarSubcategoriaPorNome;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class CriarMercadoria {

    private MercadoriaRepository repository;

    private CategoriaRepository categoriaRepository;

    private SubcategoriaRepository subcategoriaRepository;

    public CriarMercadoria(MercadoriaRepository repository, CategoriaRepository categoriaRepository,
            SubcategoriaRepository subcategoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.subcategoriaRepository = subcategoriaRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mercadoria executar(DadosCriarMercadoria dados) {
        if (repository.buscarPorCodigo(dados.codigo()).isPresent()) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Mercadoria com código " + dados.codigo() + " já cadastrada!");
        }
        BuscarCategoriaPorNome buscarCategoriaPorNome = new BuscarCategoriaPorNome(categoriaRepository);
        BuscarSubcategoriaPorNome buscarSubcategoriaPorNome = new BuscarSubcategoriaPorNome(subcategoriaRepository);
        Categoria categoria = buscarCategoriaPorNome.executar(dados.categoria());
        Subcategoria subcategoria = buscarSubcategoriaPorNome.executar(dados.subcategoria());
        Mercadoria mercadoria = new Mercadoria(null, dados.codigo(), categoria, subcategoria, dados.descricao(),
                dados.preco(),
                dados.promocao(), dados.precoPromocional(), null, true);
        return repository.criar(mercadoria);
    }

}
