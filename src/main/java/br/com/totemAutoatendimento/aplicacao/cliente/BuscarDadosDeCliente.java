package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;

public class BuscarDadosDeCliente {

	private ClienteRepository repository;

	
	public BuscarDadosDeCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public DadosDeCliente executar(Long id) {
		BuscarCliente buscarCliente = new BuscarCliente(repository);
		return new DadosDeCliente(buscarCliente.executar(id));
	}
}
