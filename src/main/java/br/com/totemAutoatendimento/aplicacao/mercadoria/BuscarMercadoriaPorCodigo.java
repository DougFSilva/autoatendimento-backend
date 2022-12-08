package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class BuscarMercadoriaPorCodigo {

    private MercadoriaRepository repository;

    public BuscarMercadoriaPorCodigo(MercadoriaRepository repository) {
        this.repository = repository;
    }

    public DadosDeMercadoria executar(String codigo) {
        Optional<Mercadoria> mercadoria = repository.buscarPorCodigo(codigo);
        if(mercadoria.isEmpty()){
            throw new ObjetoNaoEncontradoException("Mercadoria com código " + codigo + " não encontrada!");
        }
        return new DadosDeMercadoria(mercadoria.get());
    }
}
