package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CartaoEntity;

@Service
public class CartaoEntityConverter {

	public Cartao converterParaCartao(CartaoEntity entity) {
		return new Cartao(entity.getCodigo());
	}
	
	public CartaoEntity converterParaCartaoEntity(Cartao cartao) {
		return new CartaoEntity(cartao.getCodigo());
	}
}
