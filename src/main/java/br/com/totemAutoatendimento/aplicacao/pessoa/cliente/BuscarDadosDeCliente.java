package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarDadosDeCliente {

	private ClienteRepository repository;

	public BuscarDadosDeCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public DadosDeCliente executar(Long id) {
		BuscarCliente buscarCliente = new BuscarCliente(repository);
		return new DadosDeCliente(buscarCliente.executar(id));
	}
}
