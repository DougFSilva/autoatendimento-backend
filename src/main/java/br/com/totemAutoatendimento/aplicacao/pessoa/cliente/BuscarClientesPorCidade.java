package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarClientesPorCidade {

	private ClienteRepository repository;

	public BuscarClientesPorCidade(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public Page<DadosDeCliente> executar(Pageable paginacao, String cidade){
		return this.repository.buscarPorCidade(paginacao, cidade).map(DadosDeCliente::new);
	}
}
