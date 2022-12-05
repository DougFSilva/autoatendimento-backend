package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import java.util.List;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarTodosClientes {

	private ClienteRepository repository;

	public BuscarTodosClientes(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public List<DadosDeCliente> executar() {
		return this.repository.buscarTodos().stream().map(DadosDeCliente::new).toList();
	}
}
