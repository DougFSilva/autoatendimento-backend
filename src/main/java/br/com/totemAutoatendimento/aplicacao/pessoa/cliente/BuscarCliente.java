package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarCliente {
	
	private ClienteRepository repository;

	public BuscarCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public Cliente executar(Long id) {
		Optional<Cliente> cliente = repository.buscar(id);
		return cliente.orElseThrow(() -> new ObjetoNaoEncontradoException("Cliente com id " + id + " não encontrado!"));
	}

}
