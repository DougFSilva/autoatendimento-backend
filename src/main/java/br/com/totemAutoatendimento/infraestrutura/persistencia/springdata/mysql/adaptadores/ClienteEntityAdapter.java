package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.ClienteEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.ClienteEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ClienteEntity;

@Repository
public class ClienteEntityAdapter implements ClienteRepository{

	@Autowired 
	private ClienteEntityDao repository;
	
	@Autowired
	private ClienteEntityConverter clienteEntityConverter;

	@Override
	public Cliente criar(Cliente cliente) {
		ClienteEntity entity = repository.save(clienteEntityConverter.converterParaClienteEntity(cliente));
		return clienteEntityConverter.converterParaCliente(entity);
		
	}

	@Override
	public void remover(Cliente cliente) {
		repository.delete(clienteEntityConverter.converterParaClienteEntity(cliente));
	}

	@Override
	public Cliente editar(Cliente clienteAtualizado) {
		ClienteEntity entity = repository.save(clienteEntityConverter.converterParaClienteEntity(clienteAtualizado));
		return clienteEntityConverter.converterParaCliente(entity);
	}

	@Override
	public Optional<Cliente> buscar(Long id) {
		Optional<ClienteEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return Optional.of(clienteEntityConverter.converterParaCliente(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Cliente> buscarClientePorCpf(String cpf) {
		Optional<ClienteEntity> entity = repository.findByCpf(cpf);
		if(entity.isPresent()) {
			return Optional.of(clienteEntityConverter.converterParaCliente(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Cliente> buscarPorCidade(Pageable paginacao, String cidade) {
		return repository.findAllByEnderecoCidade(paginacao, cidade).map(clienteEntityConverter::converterParaCliente);
	}

	@Override
	public Page<Cliente> buscarTodos(Pageable paginacao) {
		return repository.findAll(paginacao).map(clienteEntityConverter::converterParaCliente);
	}

}
