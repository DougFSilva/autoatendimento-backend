package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class RemoverCliente {

	private ClienteRepository repository;

	public RemoverCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public void executar(Long id) {
		
	}
}
