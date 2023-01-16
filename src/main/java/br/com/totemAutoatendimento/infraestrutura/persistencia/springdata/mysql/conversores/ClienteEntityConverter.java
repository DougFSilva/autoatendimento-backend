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
	private EmailEntityConverter emailEntityConverter;

	@Autowired
	private EnderecoEntityConverter enderecoEntityConverter;

	public Cliente converterParaCliente(ClienteEntity entity) {
		Email email = emailEntityConverter.converterParaEmail(entity.getEmail());
		Endereco endereco = enderecoEntityConverter.converterParaEndereco(entity.getEndereco());
		return new Cliente(entity.getId(), 
				entity.getNome(), 
				entity.getCpf(), 
				entity.getTelefone(), 
				email, 
				endereco);
	}

	public ClienteEntity converterParaClienteEntity(Cliente cliente) {
		EmailEntity emailEntity = emailEntityConverter.converterParaEmailEntity(cliente.getEmail());
		EnderecoEntity enderecoEntity = enderecoEntityConverter.converterParaEnderecoEntity(cliente.getEndereco());
		return new ClienteEntity(cliente.getId(), 
				cliente.getNome(), 
				cliente.getCpf(), 
				cliente.getTelefone(),
				emailEntity, 
				enderecoEntity);
	}
}
