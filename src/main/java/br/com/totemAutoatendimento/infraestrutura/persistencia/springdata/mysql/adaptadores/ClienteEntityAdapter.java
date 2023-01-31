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
	private ClienteEntityDao dao;
	
	@Autowired
	private ClienteEntityConverter clienteEntityConverter;

	@Override
	public Cliente salvar(Cliente cliente) {
		ClienteEntity entity = dao.save(clienteEntityConverter.converterParaClienteEntity(cliente));
		return clienteEntityConverter.converterParaCliente(entity);
		
	}

	@Override
	public void deletar(Cliente cliente) {
		dao.delete(clienteEntityConverter.converterParaClienteEntity(cliente));
	}

	@Override
	public Optional<Cliente> buscarPeloId(Long id) {
		Optional<ClienteEntity> entity = dao.findById(id);
		if(entity.isPresent()) {
			return Optional.of(clienteEntityConverter.converterParaCliente(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Cliente> buscarClientePorCpf(String cpf) {
		Optional<ClienteEntity> entity = dao.findByCpf(cpf);
		if(entity.isPresent()) {
			return Optional.of(clienteEntityConverter.converterParaCliente(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Cliente> buscarPorCidade(Pageable paginacao, String cidade) {
		return dao.findAllByEnderecoCidade(paginacao, cidade).map(clienteEntityConverter::converterParaCliente);
	}

	@Override
	public Page<Cliente> buscarTodos(Pageable paginacao) {
		return dao.findAll(paginacao).map(clienteEntityConverter::converterParaCliente);
	}

}
