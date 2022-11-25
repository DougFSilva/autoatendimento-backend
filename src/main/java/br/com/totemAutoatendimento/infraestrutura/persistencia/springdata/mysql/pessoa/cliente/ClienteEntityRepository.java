package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

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
		// TODO Auto-generated method stub
		return null;
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
	public List<Cliente> buscarPorCidade(String cidade) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cliente> buscarTodos() {
		// TODO Auto-generated method stub
		return null;
	}

}
