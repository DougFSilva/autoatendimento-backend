package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarCliente {

	private ClienteRepository repository;

	public BuscarCliente(ClienteRepository repository) {
		this.repository = repository;
	}

	public Cliente executar(Long id) {
		return repository.buscar(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Cliente com id " + id + " não encontrado!"));
	}

}
