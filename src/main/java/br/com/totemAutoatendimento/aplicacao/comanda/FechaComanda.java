package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class FechaComanda {

    private final ComandaRepository repository;
    
    private final PedidoRepository pedidoRepository;

    public FechaComanda(ComandaRepository repository, PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public DadosDeComanda fechar(Long id, TipoPagamento tipoPagamento) {
        BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
        Comanda comanda = buscaComandaPeloId.buscar(id);
        if(!comanda.getAberta()){
            throw new ViolacaoDeIntegridadeDeDadosException("Comanda com id " + comanda.getId() + " já está fechada!");
        }
        List<Pedido> pedidos = pedidoRepository.buscarPelaComanda(comanda);
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
