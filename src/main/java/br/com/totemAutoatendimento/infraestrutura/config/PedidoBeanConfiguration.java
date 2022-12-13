package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarDadosDePedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarPedidosEntregues;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarPedidosPorData;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarTodosPedidos;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.EntregarPedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.FazerPedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.RemoverPedido;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda.ComandaEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda.PedidoEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.MercadoriaEntityRepository;

@Configuration
public class PedidoBeanConfiguration {

    @Autowired
    private PedidoEntityRepository repository;

    @Autowired
    private ComandaEntityRepository comandaRepository;

    @Autowired
    private MercadoriaEntityRepository mercadoriaRepository;

    @Bean
    public FazerPedido fazerPedido() {
        return new FazerPedido(comandaRepository, mercadoriaRepository);
    }

    @Bean
    public RemoverPedido removerPedido() {
        return new RemoverPedido(repository, comandaRepository);
    }

    @Bean
    public EntregarPedido entregarPedido() {
        return new EntregarPedido(repository);
    }

    @Bean
    public BuscarDadosDePedido buscarDadosDePedido() {
        return new BuscarDadosDePedido(repository);
    }

    @Bean
    public BuscarPedidosEntregues buscarPedidosEntregues() {
        return new BuscarPedidosEntregues(repository);
    }

    @Bean
    public BuscarPedidosPorData buscarPedidosPorData() {
        return new BuscarPedidosPorData(repository);
    }

    @Bean
    public BuscarTodosPedidos buscarTodosPedidos() {
        return new BuscarTodosPedidos(repository);
    }

}
