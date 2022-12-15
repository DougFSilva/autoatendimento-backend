package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarCategoriaPorNome;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscarSubcategoriaPorNome;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class EditarMercadoria {

    private MercadoriaRepository repository;

    private CategoriaRepository categoriaRepository;

    private SubcategoriaRepository subcategoriaRepository;

    public EditarMercadoria(MercadoriaRepository repository, CategoriaRepository categoriaRepository,
            SubcategoriaRepository subcategoriaRepository) {
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
        this.subcategoriaRepository = subcategoriaRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public DadosDeMercadoria executar(DadosEditarMercadoria dados) {
        Optional<Mercadoria> mercadoriaPorCodigo = repository.buscarPorCodigo(dados.codigo());
        if (mercadoriaPorCodigo.isPresent() && mercadoriaPorCodigo.get().getId() != dados.id()) {
            throw new ViolacaoDeIntegridadeDeDadosException(
                    "Mercadoria com código " + dados.codigo() + " já cadastrada!");
        }
        BuscarMercadoria buscarMercadoria = new BuscarMercadoria(repository);
        BuscarCategoriaPorNome buscarCategoriaPorNome = new BuscarCategoriaPorNome(categoriaRepository);
        BuscarSubcategoriaPorNome buscarSubcategoriaPorNome = new BuscarSubcategoriaPorNome(subcategoriaRepository);
        Mercadoria mercadoria = buscarMercadoria.executar(dados.id());
        Categoria categoria = buscarCategoriaPorNome.executar(dados.categoria());
        Subcategoria subcategoriaPorNome = buscarSubcategoriaPorNome.executar(dados.subcategoria());
        mercadoria.setCodigo(dados.codigo());
        mercadoria.setCategoria(categoria);
        mercadoria.setSubcategoria(subcategoriaPorNome);
        mercadoria.setDescricao(dados.descricao());
        mercadoria.setPreco(dados.preco());
        mercadoria.setPromocao(dados.promocao());
        mercadoria.setPrecoPromocional(dados.precoPromocional());
        mercadoria.setDisponivel(dados.disponivel());
        return new DadosDeMercadoria(repository.editar(mercadoria));
    }
}
