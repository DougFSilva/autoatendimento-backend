package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import java.util.List;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarClientesPorCidade {

	private ClienteRepository repository;

	public BuscarClientesPorCidade(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public List<DadosDeCliente> executar(String cidade){
		return this.repository.buscarPorCidade(cidade).stream().map(DadosDeCliente::new).toList();
	}
}
