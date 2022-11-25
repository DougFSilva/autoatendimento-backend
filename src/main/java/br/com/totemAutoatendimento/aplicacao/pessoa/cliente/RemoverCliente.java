package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class RemoverCliente {

	private ClienteRepository repository;

	public RemoverCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public void executar(Long id) {
		BuscarCliente buscaCliente = new BuscarCliente(repository);
		Cliente cliente = buscaCliente.executar(id);
		repository.remover(cliente);
	}
}
