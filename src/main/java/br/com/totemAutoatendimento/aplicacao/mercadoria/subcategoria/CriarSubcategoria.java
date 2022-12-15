package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class CriarSubcategoria {

    private SubcategoriaRepository repository;

    public CriarSubcategoria(SubcategoriaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Subcategoria executar(String nome) {
        if (repository.buscarPorNome(nome).isPresent()) {
            throw new ViolacaoDeIntegridadeDeDadosException("Subcategoria com nome " + nome + " já cadastrada!");
        }
        return repository.criar(new Subcategoria(null, nome, null));
    }
}
