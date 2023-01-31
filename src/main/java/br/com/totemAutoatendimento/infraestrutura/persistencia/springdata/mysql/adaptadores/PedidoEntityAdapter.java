package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.ComandaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.PedidoEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.PedidoEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PedidoEntity;

@Repository
public class PedidoEntityAdapter implements PedidoRepository {

	@Autowired
	private PedidoEntityDao dao;

	@Autowired
	private PedidoEntityConverter pedidoEntityConverter;

	@Autowired
	private ComandaEntityConverter comandaEntityConverter;

	@Override
	public Pedido salvar(Pedido pedido) {
		PedidoEntity entity = dao.save(pedidoEntityConverter.converterParaPedidoEntity(pedido));
		return pedidoEntityConverter.converterParaPedido(entity);
	}

	@Override
	public void deletar(Pedido pedido) {
		dao.delete(pedidoEntityConverter.converterParaPedidoEntity(pedido));
	}

	@Override
	public Optional<Pedido> buscarPeloId(Long id) {
		Optional<PedidoEntity> entity = dao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(pedidoEntityConverter.converterParaPedido(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Pedido> buscarPelaComanda(Comanda comanda) {
		return dao.findAllByComanda(comandaEntityConverter.converterParaComandaEntity(comanda))
				.stream().map(pedidoEntityConverter::converterParaPedido).toList();
	}

	@Override
	public Page<Pedido> buscarPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
		return dao.findAllByDataBetween(paginacao, dataInicial, dataFinal)
				.map(pedidoEntityConverter::converterParaPedido);
	}

	@Override
	public Page<Pedido> buscarEntregue(Pageable paginacao, Boolean entregue) {
		return dao.findAllByEntregue(paginacao, entregue).map(pedidoEntityConverter::converterParaPedido);
	}

	@Override
	public Page<Pedido> buscarTodos(Pageable paginacao) {
		return dao.findAll(paginacao).map(pedidoEntityConverter::converterParaPedido);
	}

}
