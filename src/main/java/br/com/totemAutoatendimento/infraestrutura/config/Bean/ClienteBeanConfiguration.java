package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.cliente.BuscaDadosDeClientes;
import br.com.totemAutoatendimento.aplicacao.cliente.CriaCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.EditaCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.RemoveCliente;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.ClienteEntityAdapter;

@Configuration
public class ClienteBeanConfiguration {

	@Autowired
	private ClienteEntityAdapter clienteEntityAdapter;

	@Bean
	public CriaCliente criaCliente() {
		return new CriaCliente(clienteEntityAdapter);
	}

	@Bean
	public RemoveCliente removeCliente() {
		return new RemoveCliente(clienteEntityAdapter);
	}

	@Bean
	public EditaCliente editaCliente() {
		return new EditaCliente(clienteEntityAdapter);
	}

	@Bean
	public BuscaDadosDeClientes buscaDadosDeClientes() {
		return new BuscaDadosDeClientes(clienteEntityAdapter);
	}

}
