package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

public class FecharComanda {

    private ComandaRepository repository;

    public FecharComanda(ComandaRepository repository) {
        this.repository = repository;
    }

    public DadosDeComanda executar(DadosFecharComanda dados) {
        Optional<Comanda> comanda = repository.buscar(dados.id());
        if (comanda.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Comanda com id " + dados.id() + " não encontrada!");
        }
        if(!comanda.get().getAberta()){
            throw new ViolacaoDeIntegridadeDeDadosException("Comanda com id " + comanda.get().getId() + " já está fechada!");
        }
        List<Pedido> pedidos = comanda.get().getPedidos();
        pedidos.forEach(pedido -> {
            if (!pedido.getEntregue()) {
                throw new RegrasDeNegocioException("Não é possível fechar comanda pois há pedidos não entregues!");
            }
        });
        comanda.get().aplicarDesconto();
        comanda.get().setAberta(false);
        comanda.get().setTipoPagamento(dados.tipoPagamento());
        comanda.get().setFechamento(LocalDateTime.now());
        return new DadosDeComanda(repository.editar(comanda.get()));
    }
}
