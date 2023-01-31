package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.comanda.AbreComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.AplicaDescontoEmComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscaDadosDeComandas;
import br.com.totemAutoatendimento.aplicacao.comanda.DeletaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.FechaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.ReabreComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoveDescontoDaComanda;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
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
	private StandardLogger standardLogger;

	@Bean
	 AbreComanda abreComanda() {
		return new AbreComanda(comandaEntityAdapter, clienteEntityAdapter, cartaoEntityAdapter, standardLogger);
	}

	@Bean
	AplicaDescontoEmComanda aplicaDescontoEmComanda() {
		return new AplicaDescontoEmComanda(comandaEntityAdapter, standardLogger);
	}

	@Bean
	RemoveDescontoDaComanda removeDescontoDaComanda() {
		return new RemoveDescontoDaComanda(comandaEntityAdapter, standardLogger);
	}

	@Bean
	FechaComanda fechaComanda() {
		return new FechaComanda(comandaEntityAdapter, pedidoEntityAdapter, standardLogger);
	}

	@Bean
	ReabreComanda reabreComanda() {
		return new ReabreComanda(comandaEntityAdapter, standardLogger);
	}

	@Bean
	DeletaComanda removeComanda() {
		return new DeletaComanda(comandaEntityAdapter, standardLogger);
	}
	
	@Bean
	BuscaDadosDeComandas buscaDadosDeComandas() {
		return new BuscaDadosDeComandas(comandaEntityAdapter, clienteEntityAdapter);
	}

}
