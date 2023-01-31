package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.CartaoEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.CartaoEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CartaoEntity;

@Repository
public class CartaoEntityAdapter implements CartaoRepository{

	@Autowired
	private CartaoEntityDao dao;
	
	@Autowired
	private CartaoEntityConverter cartaoEntityConverter;
	
	@Override
	public Cartao salvar(Cartao cartao) {
		CartaoEntity entity = dao.save(cartaoEntityConverter.converterParaCartaoEntity(cartao));
		return cartaoEntityConverter.converterParaCartao(entity);
	}

	@Override
	public void deletar(Cartao cartao) {
		CartaoEntity entity = dao.save(cartaoEntityConverter.converterParaCartaoEntity(cartao));
		dao.delete(entity);
	}

	@Override
	public Optional<Cartao> buscarPeloCodigo(String codigo) {
		Optional<CartaoEntity> entity = dao.findByCodigo(codigo);
		if(entity.isPresent()) {
			return Optional.of(cartaoEntityConverter.converterParaCartao(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Cartao> buscarTodos() {
		List<CartaoEntity> entities = dao.findAll();
		return entities.stream().map(cartaoEntityConverter::converterParaCartao).toList();
	}

}
