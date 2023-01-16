package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class BuscaClientePeloId {

	private final ClienteRepository repository;

	public BuscaClientePeloId(ClienteRepository repository) {
		this.repository = repository;
	}

	public Cliente buscar(Long id) {
		return repository.buscar(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Cliente com id " + id + " não encontrado!"));
	}

}
