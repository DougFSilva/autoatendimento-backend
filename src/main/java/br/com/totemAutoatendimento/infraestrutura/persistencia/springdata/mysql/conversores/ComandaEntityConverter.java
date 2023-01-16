package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CartaoEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ClienteEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ComandaEntity;

@Service
public class ComandaEntityConverter {

	@Autowired
	private ClienteEntityConverter clienteEntityConverter;
	
	@Autowired
	private CartaoEntityConverter cartaoEntityConverter;

	public Comanda converterParaComanda(ComandaEntity entity) {
		Cliente cliente = clienteEntityConverter.converterParaCliente(entity.getCliente());
		Cartao cartao = cartaoEntityConverter.converterParaCartao(entity.getCartao());
		return new Comanda(entity.getId(), 
				cartao, 
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
		CartaoEntity cartaoEntity = cartaoEntityConverter.converterParaCartaoEntity(comanda.getCartao());
		return new ComandaEntity(comanda.getId(), 
				cartaoEntity, 
				clienteEntity, 
				comanda.getAbertura(),
				comanda.getFechamento(), 
				comanda.getAberta(), 
				comanda.getTipoPagamento(), 
				comanda.getValor(),
				comanda.getDesconto());
	}
}
