package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class RemoveCliente {

	private final ClienteRepository repository;

	public RemoveCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public void remover(Long id) {
		BuscaClientePeloId buscaClientePeloId = new BuscaClientePeloId(repository);
		Cliente cliente = buscaClientePeloId.buscar(id);
		repository.remover(cliente);
	}
}
