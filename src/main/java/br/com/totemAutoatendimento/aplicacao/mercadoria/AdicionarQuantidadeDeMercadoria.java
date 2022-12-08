package br.com.totemAutoatendimento.aplicacao.mercadoria;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class AdicionarQuantidadeDeMercadoria {
    
    private MercadoriaRepository repository;

    public AdicionarQuantidadeDeMercadoria(MercadoriaRepository repository){
        this.repository = repository;
    }

    public DadosDeMercadoria executar(Long id, Integer quantidade){
        BuscarMercadoria buscarMercadoria = new BuscarMercadoria(repository);
        Mercadoria mercadoria = buscarMercadoria.executar(id);
        mercadoria.setQuantidade(mercadoria.getQuantidade() + quantidade);
        return new DadosDeMercadoria(repository.editar(mercadoria));
    }
}
