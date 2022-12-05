package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarTodosClientes {

	private ClienteRepository repository;

	public BuscarTodosClientes(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public Page<DadosDeCliente> executar(Pageable paginacao) {
		return this.repository.buscarTodos(paginacao).map(DadosDeCliente::new);
	}
}
