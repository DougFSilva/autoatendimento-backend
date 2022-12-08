package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscarCategoriaPorNome;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscarMercadoriasPorCategoria {
    
    private MercadoriaRepository repository;

    private CategoriaRepository categoriaRepository;

    public BuscarMercadoriasPorCategoria(MercadoriaRepository repository, CategoriaRepository categoriaRepository){
        this.repository = repository;
        this.categoriaRepository = categoriaRepository;
    }

    public Page<DadosDeMercadoria> executar(Pageable paginacao, String nomeDaCategoria){
        BuscarCategoriaPorNome buscarCategoriaPorNome = new BuscarCategoriaPorNome(categoriaRepository);
        Categoria categoria = buscarCategoriaPorNome.executar(nomeDaCategoria);
        return repository.buscarPorCategoria(paginacao, categoria).map(DadosDeMercadoria::new);
    }
}
