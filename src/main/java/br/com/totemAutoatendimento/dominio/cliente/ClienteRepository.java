package br.com.totemAutoatendimento.dominio.cliente;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClienteRepository {

	Cliente criar(Cliente cliente);
	
	void remover(Cliente cliente);
	
	Cliente editar(Cliente clienteAtualizado);
	
	Optional<Cliente> buscarPeloId(Long id);
	
	Optional<Cliente> buscarClientePorCpf(String cpf);
	
	Page<Cliente> buscarPorCidade(Pageable paginacao, String cidade);
	
	Page<Cliente> buscarTodos(Pageable paginacao);
}
