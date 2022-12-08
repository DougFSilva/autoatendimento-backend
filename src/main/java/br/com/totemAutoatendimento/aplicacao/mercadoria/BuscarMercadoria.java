package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class BuscarMercadoria {

    private MercadoriaRepository repository;

    public BuscarMercadoria(MercadoriaRepository repository) {
        this.repository = repository;
    }

    public Mercadoria executar(Long id) {
        Optional<Mercadoria> mercadoria = repository.buscar(id);
        return mercadoria
                .orElseThrow(() -> new ObjetoNaoEncontradoException("Mercadoria com id " + id + " não encontrada!"));
    }
}
