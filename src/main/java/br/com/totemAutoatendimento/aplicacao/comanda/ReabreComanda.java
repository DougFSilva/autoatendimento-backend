package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class ReabreComanda {
    
    private final ComandaRepository repository;

    public ReabreComanda(ComandaRepository repository){
        this.repository = repository;
    }
   
    @Transactional
    public DadosDeComanda reabrir(Long id){
        BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
        Comanda comanda = buscaComandaPeloId.buscar(id);
        comanda.setAberta(true);
        comanda.setTipoPagamento(TipoPagamento.NAO_PAGO);
        comanda.removerDesconto();
        comanda.setDesconto(0f);
        comanda.setFechamento(null);
        return new DadosDeComanda(repository.editar(comanda));
    }
    
}
