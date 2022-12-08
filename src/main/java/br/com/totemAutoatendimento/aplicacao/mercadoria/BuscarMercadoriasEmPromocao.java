package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.DadosDeMercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class BuscarMercadoriasEmPromocao {
    
    private MercadoriaRepository repository;

    public BuscarMercadoriasEmPromocao(MercadoriaRepository repository){
        this.repository = repository;
    }

    public Page<DadosDeMercadoria> executar(Pageable paginacao, Boolean promocao){
        return repository.buscarEmPromocao(paginacao, promocao).map(DadosDeMercadoria::new);
    }
}
