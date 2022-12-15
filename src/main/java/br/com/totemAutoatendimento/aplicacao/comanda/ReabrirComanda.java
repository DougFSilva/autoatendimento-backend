package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

public class ReabrirComanda {
    
    private ComandaRepository repository;

    public ReabrirComanda(ComandaRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    @Transactional
    public DadosDeComanda executar(Long id){
        BuscarComanda buscarComanda = new BuscarComanda(repository);
        Comanda comanda = buscarComanda.executar(id);
        comanda.setAberta(true);
        comanda.setTipoPagamento(TipoPagamento.NAO_PAGO);
        comanda.removerDesconto();
        comanda.setDesconto(0f);
        return new DadosDeComanda(repository.editar(comanda));
    }
    
}
