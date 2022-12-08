package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscarMercadoria;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.DadosDeMercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class RemoverQuantidadeDeMercadoria {

    private MercadoriaRepository repository;

    public RemoverQuantidadeDeMercadoria(MercadoriaRepository repository) {
        this.repository = repository;
    }

    public DadosDeMercadoria executar(Long id, Integer quantidade) {
        BuscarMercadoria buscarMercadoria = new BuscarMercadoria(repository);
        Mercadoria mercadoria = buscarMercadoria.executar(id);
        if (mercadoria.getQuantidade() < quantidade) {
            throw new ViolacaoDeIntegridadeDeDadosException("Impossível remover " + quantidade
                    + " mercadoria(s), pois há somente " + mercadoria.getQuantidade() + "!");
        }
        mercadoria.setQuantidade(mercadoria.getQuantidade() - quantidade);
        return new DadosDeMercadoria(repository.editar(mercadoria));
    }
}
