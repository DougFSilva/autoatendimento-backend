package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.comanda.AplicarDescontoEmComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaAbertaPorCartao;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaPorCliente;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaPorData;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaPorTipoDePagamento;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandasAbertas;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarDadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarTodasComandas;
import br.com.totemAutoatendimento.aplicacao.comanda.CriarComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.FecharComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.ReabrirComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoverComanda;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda.ComandaEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente.ClienteEntityRepository;

@Configuration
public class ComandaBeanConfiguration {
    
    @Autowired
    private ComandaEntityRepository repository;

    @Autowired
    private ClienteEntityRepository clienteRepository;

    @Bean
    public BuscarComandaAbertaPorCartao buscarComandaAbertaPorCartao(){
        return new BuscarComandaAbertaPorCartao(repository);
    }

    @Bean
    public BuscarComandaPorCliente buscarComandaPorCliente(){
        return new BuscarComandaPorCliente(repository, clienteRepository);
    }

    @Bean
    public BuscarComandaPorData buscarComandaPorData() {
        return new BuscarComandaPorData(repository);
    }

    @Bean
    public BuscarComandaPorTipoDePagamento buscarComandaPorTipoDePagamento() {
        return new BuscarComandaPorTipoDePagamento(repository);
    }

    @Bean
    public BuscarComandasAbertas buscarComandasAbertas() {
        return new BuscarComandasAbertas(repository);
    }

    @Bean
    public BuscarDadosDeComanda buscarDadosDeComanda() {
        return new BuscarDadosDeComanda(repository);
    }

    @Bean
    public BuscarTodasComandas buscarTodasComandas() {
        return new BuscarTodasComandas(repository);
    }

    @Bean
    public CriarComanda criarComanda() {
        return new CriarComanda(repository, clienteRepository);
    }

    @Bean
    public AplicarDescontoEmComanda aplicarDescontoEmComanda() {
        return new AplicarDescontoEmComanda(repository);
    }

    @Bean
    public FecharComanda fecharComanda() {
        return new FecharComanda(repository);
    }

    @Bean
    public ReabrirComanda reabrirComanda() {
        return new ReabrirComanda(repository);
    }

    @Bean
    public RemoverComanda removerComanda() {
        return new RemoverComanda(repository);
    }

}
