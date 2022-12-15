package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscarSubcategoriaPorNome {

    private SubcategoriaRepository repository;

    public BuscarSubcategoriaPorNome(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Subcategoria executar(String nome) {
        Optional<Subcategoria> subcategoria = repository.buscarPorNome(nome);
        return subcategoria.orElseThrow(
                () -> new ObjetoNaoEncontradoException("Subcategoria com nome " + nome + " não encontrada!"));
    }
}
