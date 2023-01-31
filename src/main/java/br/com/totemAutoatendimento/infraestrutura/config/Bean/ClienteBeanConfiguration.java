package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.cliente.BuscaDadosDeClientes;
import br.com.totemAutoatendimento.aplicacao.cliente.CadastraCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.DeletaCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.EditaCliente;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.ClienteEntityAdapter;

@Configuration
public class ClienteBeanConfiguration {

	@Autowired
	private ClienteEntityAdapter clienteEntityAdapter;
	
	@Autowired
	private StandardLogger standardLogger;

	@Bean
	CadastraCliente criaCliente() {
		return new CadastraCliente(clienteEntityAdapter, standardLogger);
	}

	@Bean
	DeletaCliente removeCliente() {
		return new DeletaCliente(clienteEntityAdapter, standardLogger);
	}

	@Bean
	EditaCliente editaCliente() {
		return new EditaCliente(clienteEntityAdapter, standardLogger);
	}

	@Bean
	BuscaDadosDeClientes buscaDadosDeClientes() {
		return new BuscaDadosDeClientes(clienteEntityAdapter);
	}

}
