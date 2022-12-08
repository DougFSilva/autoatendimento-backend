package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscarSubcategoriaPorNome;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscarMercadoriasPorSubcategoria {

    private MercadoriaRepository repository;

    private SubcategoriaRepository subcategoriaRepository;

    public BuscarMercadoriasPorSubcategoria(MercadoriaRepository repository,
            SubcategoriaRepository subcategoriaRepository) {
        this.repository = repository;
        this.subcategoriaRepository = subcategoriaRepository;
    }

    public Page<DadosDeMercadoria> executar(Pageable paginacao, String nomeDaSubcategoria) {
        BuscarSubcategoriaPorNome buscarSubcategoriaPorNome = new BuscarSubcategoriaPorNome(subcategoriaRepository);
        Subcategoria subcategoria = buscarSubcategoriaPorNome.executar(nomeDaSubcategoria);
        return repository.buscarPorSubcategoria(paginacao, subcategoria).map(DadosDeMercadoria::new);
    }
}
