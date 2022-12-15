package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.cliente;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;

@Repository
public class ClienteEntityRepository implements ClienteRepository{

	@Autowired 
	private ClienteEntityJpaRepository repository;

	@Override
	public Cliente criar(Cliente cliente) {
		return repository.save(new ClienteEntity(cliente)).converterParaCliente();
		
	}

	@Override
	public void remover(Cliente cliente) {
		repository.delete(new ClienteEntity(cliente));
		
	}

	@Override
	public Cliente editar(Cliente clienteAtualizado) {
		return repository.save(new ClienteEntity(clienteAtualizado)).converterParaCliente();
	}

	@Override
	public Optional<Cliente> buscar(Long id) {
		Optional<ClienteEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaCliente());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Cliente> buscarClientePorCpf(String cpf) {
		Optional<ClienteEntity> entity = repository.findByCpf(cpf);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaCliente());
		}
		return Optional.empty();
	}

	@Override
	public Page<Cliente> buscarPorCidade(Pageable paginacao, String cidade) {
		return repository.findAllByEnderecoCidade(paginacao, cidade).map(ClienteEntity::converterParaCliente);
	}

	@Override
	public Page<Cliente> buscarTodos(Pageable paginacao) {
		return repository.findAll(paginacao).map(ClienteEntity::converterParaCliente);
	}

}
