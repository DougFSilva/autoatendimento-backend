package br.com.totemAutoatendimento.dominio.pessoa.cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository {

	Cliente criar(Cliente cliente);
	
	void remover(Cliente cliente);
	
	Cliente editar(Cliente clienteAtualizado);
	
	Optional<Cliente> buscar(Long id);
	
	Optional<Cliente> buscarClientePorCpf(String cpf);
	
	List<Cliente> buscarPorCidade(String cidade);
	
	List<Cliente> buscarTodos();
}
