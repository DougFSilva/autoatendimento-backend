package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.comanda.AplicaDescontoEmComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscaDadosDeComandas;
import br.com.totemAutoatendimento.aplicacao.comanda.CriaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.FechaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.ReabreComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoveComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoveDescontoDaComanda;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.CartaoEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.ClienteEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.ComandaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.PedidoEntityAdapter;

@Configuration
public class ComandaBeanConfiguration {

	@Autowired
	private ComandaEntityAdapter comandaEntityAdapter;

	@Autowired
	private ClienteEntityAdapter clienteEntityAdapter;

	@Autowired
	private PedidoEntityAdapter pedidoEntityAdapter;
	
	@Autowired
	private CartaoEntityAdapter cartaoEntityAdapter;
	
	@Autowired
	private LoggerAdapter loggerAdapter;

	@Bean
	public CriaComanda criaComanda() {
		return new CriaComanda(comandaEntityAdapter, clienteEntityAdapter, cartaoEntityAdapter, loggerAdapter);
	}

	@Bean
	public AplicaDescontoEmComanda aplicaDescontoEmComanda() {
		return new AplicaDescontoEmComanda(comandaEntityAdapter, loggerAdapter);
	}

	@Bean
	public RemoveDescontoDaComanda removeDescontoDaComanda() {
		return new RemoveDescontoDaComanda(comandaEntityAdapter, loggerAdapter);
	}

	@Bean
	public FechaComanda fechaComanda() {
		return new FechaComanda(comandaEntityAdapter, pedidoEntityAdapter, loggerAdapter);
	}

	@Bean
	public ReabreComanda reabreComanda() {
		return new ReabreComanda(comandaEntityAdapter, loggerAdapter);
	}

	@Bean
	public RemoveComanda removeComanda() {
		return new RemoveComanda(comandaEntityAdapter, loggerAdapter);
	}
	
	@Bean
	public BuscaDadosDeComandas buscaDadosDeComandas() {
		return new BuscaDadosDeComandas(comandaEntityAdapter, clienteEntityAdapter);
	}

}
