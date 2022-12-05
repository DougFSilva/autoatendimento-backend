package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscarSubcategoria {

    private SubcategoriaRepository repository;

    public BuscarSubcategoria(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    public Subcategoria executar(Long id) {
        Optional<Subcategoria> subcategoria = repository.buscar(id);
        return subcategoria.orElseThrow(
                () -> new ViolacaoDeIntegridadeDeDadosException("Subcategoria com id " + id + " não encontrada!"));
    }
}
