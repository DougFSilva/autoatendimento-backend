package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscarTodasSubcategorias {

    private SubcategoriaRepository repository;

    public BuscarTodasSubcategorias(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    public Page<Subcategoria> executar(Pageable paginacao){
        return repository.buscarTodas(paginacao);
    }
}
