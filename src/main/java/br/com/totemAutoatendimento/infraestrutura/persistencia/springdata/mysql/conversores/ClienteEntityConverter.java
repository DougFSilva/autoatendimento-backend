package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ClienteEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.EmailEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.EnderecoEntity;

@Service
public class ClienteEntityConverter {

	@Autowired
	private EnderecoEntityConverter enderecoEntityConverter;

	public Cliente converterParaCliente(ClienteEntity entity) {
		Endereco endereco = enderecoEntityConverter.converterParaEndereco(entity.getEndereco());
		return new Cliente(entity.getId(), 
				entity.getNome(), 
				entity.getCpf(), 
				entity.getTelefone(), 
				new Email(entity.getEmail().getEndereco()), 
				endereco);
	}

	public ClienteEntity converterParaClienteEntity(Cliente cliente) {
		EnderecoEntity enderecoEntity = enderecoEntityConverter.converterParaEnderecoEntity(cliente.getEndereco());
		return new ClienteEntity(cliente.getId(), 
				cliente.getNome(), 
				cliente.getCpf(), 
				cliente.getTelefone(),
				new EmailEntity(cliente.getEmail().getEndereco()), 
				enderecoEntity);
	}
}
