package br.com.totemAutoatendimento.aplicacao.cliente;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;

public class BuscarTodosClientes {

	private ClienteRepository repository;

	public BuscarTodosClientes(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Page<DadosDeCliente> executar(Pageable paginacao) {
		return this.repository.buscarTodos(paginacao).map(DadosDeCliente::new);
	}
}
