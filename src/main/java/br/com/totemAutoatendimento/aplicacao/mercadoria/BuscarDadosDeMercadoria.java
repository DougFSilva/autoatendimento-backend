package br.com.totemAutoatendimento.aplicacao.mercadoria;

import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class BuscarDadosDeMercadoria {
    
    private MercadoriaRepository repository;

    public BuscarDadosDeMercadoria(MercadoriaRepository repository){
        this.repository = repository;
    }

    public DadosDeMercadoria executar(Long id){
        BuscarMercadoria buscarMercadoria = new BuscarMercadoria(repository);
        return new DadosDeMercadoria(buscarMercadoria.executar(id));
    }
}
