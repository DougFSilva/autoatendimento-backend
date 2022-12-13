package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

public class ReabrirComanda {
    
    private ComandaRepository repository;

    public ReabrirComanda(ComandaRepository repository){
        this.repository = repository;
    }

    public DadosDeComanda executar(Long id){
        BuscarComanda buscarComanda = new BuscarComanda(repository);
        Comanda comanda = buscarComanda.executar(id);
        comanda.setAberta(true);
        comanda.setTipoPagamento(TipoPagamento.NAO_PAGO);
        return new DadosDeComanda(repository.editar(comanda));
    }
    
}
