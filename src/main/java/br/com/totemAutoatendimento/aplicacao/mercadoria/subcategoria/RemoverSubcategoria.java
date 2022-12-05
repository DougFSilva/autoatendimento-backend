package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class RemoverSubcategoria {

    private SubcategoriaRepository repository;

    public RemoverSubcategoria(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    public void executar(Long id){
        BuscarSubcategoria buscarSubcategoria = new BuscarSubcategoria(repository);
        Subcategoria subcategoria = buscarSubcategoria.executar(id);
        repository.remover(subcategoria);
    }
}
