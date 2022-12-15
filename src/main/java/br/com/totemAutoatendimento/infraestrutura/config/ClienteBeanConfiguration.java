package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.cliente.BuscarClientePorCpf;
import br.com.totemAutoatendimento.aplicacao.cliente.BuscarClientesPorCidade;
import br.com.totemAutoatendimento.aplicacao.cliente.BuscarDadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.BuscarTodosClientes;
import br.com.totemAutoatendimento.aplicacao.cliente.CriarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.EditarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.RemoverCliente;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.cliente.ClienteEntityRepository;

@Configuration
public class ClienteBeanConfiguration {

    @Autowired
    private ClienteEntityRepository repository;

    @Bean
    public CriarCliente criarCliente() {
        return new CriarCliente(repository);
    }

    @Bean
    public RemoverCliente removerCliente() {
        return new RemoverCliente(repository);
    }

    @Bean
    public EditarCliente editarCliente() {
        return new EditarCliente(repository);
    }

    @Bean
    public BuscarDadosDeCliente buscarDadosDeCliente() {
        return new BuscarDadosDeCliente(repository);
    }

    @Bean
    public BuscarClientePorCpf buscarClientePorCpf() {
        return new BuscarClientePorCpf(repository);
    }

    @Bean
    public BuscarClientesPorCidade buscarClientesPorCidade() {
        return new BuscarClientesPorCidade(repository);
    }

    @Bean
    public BuscarTodosClientes buscarTodosClientes() {
        return new BuscarTodosClientes(repository);
    }

}
