package br.com.totemAutoatendimento.aplicacao.mercadoria;

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
        mercadoria.removerQuantidade(quantidade);
        return new DadosDeMercadoria(repository.editar(mercadoria));
    }
}
