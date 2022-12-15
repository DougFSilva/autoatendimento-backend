package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscarCliente {

	private ClienteRepository repository;

	public BuscarCliente(ClienteRepository repository) {
		this.repository = repository;
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Cliente executar(Long id) {
		return repository.buscar(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Cliente com id " + id + " não encontrado!"));
	}

}
