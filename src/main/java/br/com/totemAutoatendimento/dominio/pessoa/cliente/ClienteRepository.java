package br.com.totemAutoatendimento.dominio.pessoa.cliente;

import java.util.List;

public interface ClienteRepository {

	Cliente criar(Cliente cliente);
	
	void remover(Long id);
	
	Cliente editar(Long id, Cliente clienteAtualizado);
	
	Cliente buscar(Long id);
	
	List<Cliente> buscarPorCidade(String cidade);
}
