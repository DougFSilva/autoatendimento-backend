package br.com.totemAutoatendimento.aplicacao.pedido;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class VerificarDisponibilidadeDeMercadoria {
    
    private MercadoriaRepository repository;

    public VerificarDisponibilidadeDeMercadoria(MercadoriaRepository repository){
        this.repository = repository;
    }

    public Mercadoria executar(String codigo){
        Optional<Mercadoria> mercadoria = repository.buscarPorCodigo(codigo);
        if (mercadoria.isEmpty()) {
            throw new ObjetoNaoEncontradoException(
                    "Mercadoria com código " + codigo + " não encontrada!");
        }
        if (!mercadoria.get().getDisponivel()) {
            throw new RegrasDeNegocioException(
                    "Não é possível realizar pedido, pois a mercadoria está indisponível!");
        }
        return mercadoria.get();
    }
}
