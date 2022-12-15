package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;

public class RemoverCliente {

	private ClienteRepository repository;

	public RemoverCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	@Transactional
	public void executar(Long id) {
		BuscarCliente buscaCliente = new BuscarCliente(repository);
		Cliente cliente = buscaCliente.executar(id);
		repository.remover(cliente);
	}
}
