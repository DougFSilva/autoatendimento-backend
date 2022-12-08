package br.com.totemAutoatendimento.aplicacao.mercadoria;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class RemoverMercadoria {
    
    private MercadoriaRepository repository;

    public RemoverMercadoria(MercadoriaRepository repository){
        this.repository = repository;
    }

    public void executar(Long id){
        BuscarMercadoria buscarMercadoria = new BuscarMercadoria(repository);
        Mercadoria mercadoria = buscarMercadoria.executar(id);
        repository.remover(mercadoria);
    }
}
