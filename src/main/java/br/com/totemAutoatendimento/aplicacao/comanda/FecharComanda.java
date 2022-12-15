package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;

public class FecharComanda {

    private ComandaRepository repository;

    public FecharComanda(ComandaRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    @Transactional
    public DadosDeComanda executar(Long id, TipoPagamento tipoPagamento) {
        BuscarComanda buscarComanda = new BuscarComanda(repository);
        Comanda comanda = buscarComanda.executar(id);
        if(!comanda.getAberta()){
            throw new ViolacaoDeIntegridadeDeDadosException("Comanda com id " + comanda.getId() + " já está fechada!");
        }
        List<Pedido> pedidos = comanda.getPedidos();
        pedidos.forEach(pedido -> {
            if (!pedido.getEntregue()) {
                throw new RegrasDeNegocioException("Não é possível fechar comanda pois há pedidos não entregues!");
            }
        });
        comanda.setAberta(false);
        comanda.setTipoPagamento(tipoPagamento);
        comanda.setFechamento(LocalDateTime.now());
        return new DadosDeComanda(repository.editar(comanda));
    }
}
