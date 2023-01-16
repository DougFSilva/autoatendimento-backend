package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ClienteEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ComandaEntity;

@Service
public class ComandaEntityConverter {

	@Autowired
	private ClienteEntityConverter clienteEntityConverter;

	public Comanda converterParaComanda(ComandaEntity entity) {
		Cliente cliente = clienteEntityConverter.converterParaCliente(entity.getCliente());
		return new Comanda(entity.getId(), 
				entity.getCartao(), 
				cliente, 
				entity.getAbertura(),
				entity.getFechamento(), 
				entity.getAberta(), 
				entity.getTipoPagamento(), 
				entity.getValor(),
				entity.getDesconto());
	}

	public ComandaEntity converterParaComandaEntity(Comanda comanda) {
		ClienteEntity clienteEntity = clienteEntityConverter.converterParaClienteEntity(comanda.getCliente());
		return new ComandaEntity(comanda.getId(), 
				comanda.getCartao(), 
				clienteEntity, 
				comanda.getAbertura(),
				comanda.getFechamento(), 
				comanda.getAberta(), 
				comanda.getTipoPagamento(), 
				comanda.getValor(),
				comanda.getDesconto());
	}
}
