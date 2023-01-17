package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ComandaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.MercadoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PedidoEntity;

@Service
public class PedidoEntityConverter {
	
	@Autowired
	private ComandaEntityConverter comandaEntityConverter;
	
	@Autowired
	private MercadoriaEntityConverter mercadoriaEntityConverter;

	public Pedido converterParaPedido(PedidoEntity entity) {
		Comanda comanda = comandaEntityConverter.converterParaComanda(entity.getComanda());
		Mercadoria mercadoria = mercadoriaEntityConverter.converterParaMercadoria(entity.getMercadoria());
		return new Pedido(entity.getId(),
				comanda,
				mercadoria,
				entity.getMesa(),
				entity.getQuantidade(),
				entity.getData(),
				entity.getTempoPedido(),
				entity.getTempoEntrega(),
				entity.getEntregue(),
				entity.getValor());
	}

	public PedidoEntity converterParaPedidoEntity(Pedido pedido) {
		ComandaEntity comandaEntity = comandaEntityConverter.converterParaComandaEntity(pedido.getComanda());
		MercadoriaEntity mercadoriaEntity = mercadoriaEntityConverter.converterParaMercadoriaEntity(pedido.getMercadoria());
		return new PedidoEntity(pedido.getId(),
				comandaEntity,
				mercadoriaEntity,
				pedido.getMesa(),
				pedido.getQuantidade(),
				pedido.getData(),
				pedido.getTempoPedido(),
				pedido.getTempoEntrega(),
				pedido.getEntregue(),
				pedido.getValor());
	}
}
