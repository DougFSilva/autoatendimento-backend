package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscarTodasSubcategorias {

    private SubcategoriaRepository repository;

    public BuscarTodasSubcategorias(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Page<Subcategoria> executar(Pageable paginacao){
        return repository.buscarTodas(paginacao);
    }
}
