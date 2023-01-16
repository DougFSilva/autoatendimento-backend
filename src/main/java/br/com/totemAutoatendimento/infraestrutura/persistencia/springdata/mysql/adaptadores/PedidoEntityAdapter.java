package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.time.LocalDate;
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
	private PedidoEntityDao repository;

	@Autowired
	private PedidoEntityConverter pedidoEntityConverter;

	@Autowired
	private ComandaEntityConverter comandaEntityConverter;

	@Override
	public Pedido criar(Pedido pedido) {
		PedidoEntity entity = repository.save(pedidoEntityConverter.converterParaPedidoEntity(pedido));
		return pedidoEntityConverter.converterParaPedido(entity);
	}

	@Override
	public void remover(Pedido pedido) {
		repository.delete(pedidoEntityConverter.converterParaPedidoEntity(pedido));
	}

	@Override
	public Pedido editar(Pedido pedidoAtualizado) {
		PedidoEntity entity = repository.save(pedidoEntityConverter.converterParaPedidoEntity(pedidoAtualizado));
		return pedidoEntityConverter.converterParaPedido(entity);
	}

	@Override
	public Optional<Pedido> buscarPeloId(Long id) {
		Optional<PedidoEntity> entity = repository.findById(id);
		if (entity.isPresent()) {
			return Optional.of(pedidoEntityConverter.converterParaPedido(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Pedido> buscarPelaComanda(Pageable paginacao, Comanda comanda) {
		return repository.findAllByComanda(paginacao, comandaEntityConverter.converterParaComandaEntity(comanda))
				.map(pedidoEntityConverter::converterParaPedido);
	}

	@Override
	public Page<Pedido> buscarPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
		return repository.findAllByDataBetween(paginacao, dataInicial, dataFinal)
				.map(pedidoEntityConverter::converterParaPedido);
	}

	@Override
	public Page<Pedido> buscarEntregue(Pageable paginacao, Boolean entregue) {
		return repository.findAllByEntregue(paginacao, entregue).map(pedidoEntityConverter::converterParaPedido);
	}

	@Override
	public Page<Pedido> buscarTodos(Pageable paginacao) {
		return repository.findAll(paginacao).map(pedidoEntityConverter::converterParaPedido);
	}

}
