package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.CartaoEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.ClienteEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.ComandaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.ComandaEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CartaoEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ComandaEntity;

@Repository
public class ComandaEntityAdapter implements ComandaRepository {

	@Autowired
	private ComandaEntityDao dao;

	@Autowired
	private ComandaEntityConverter comandaEntityConverter;

	@Autowired
	private ClienteEntityConverter clienteEntityConverter;
	
	@Autowired
	private CartaoEntityConverter cartaoEntityConverter;

	@Override
	public Comanda salvar(Comanda comanda) {
		ComandaEntity entity = dao.save(comandaEntityConverter.converterParaComandaEntity(comanda));
		return comandaEntityConverter.converterParaComanda(entity);
	}

	@Override
	public void deletar(Comanda comanda) {
		dao.delete(comandaEntityConverter.converterParaComandaEntity(comanda));
	}

	@Override
	public Optional<Comanda> buscarPeloId(Long id) {
		Optional<ComandaEntity> entity = dao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(comandaEntityConverter.converterParaComanda(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Comanda> buscarPeloCartao(String codigoCartao, Boolean aberta) {
		CartaoEntity cartaoEntity = cartaoEntityConverter.converterParaCartaoEntity(new Cartao(codigoCartao));
		Optional<ComandaEntity> entity = dao.findByCartaoAndAberta(cartaoEntity, aberta);
		if (entity.isPresent()) {
			return Optional.of(comandaEntityConverter.converterParaComanda(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Comanda> buscarAbertas(Pageable paginacao, Boolean aberta) {
		return dao.findAllByAberta(paginacao, aberta).map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPeloCliente(Pageable paginacao, Cliente cliente) {
		return dao.findAllByCliente(paginacao, clienteEntityConverter.converterParaClienteEntity(cliente))
				.map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPelaData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return dao.findAllByAberturaBetween(paginacao, dataInicial, dataFinal)
				.map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPeloTipoDePagamento(Pageable paginacao, TipoPagamento tipoPagamento) {
		return dao.findAllByTipoPagamento(paginacao, tipoPagamento)
				.map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarTodas(Pageable paginacao) {
		return dao.findAll(paginacao).map(comandaEntityConverter::converterParaComanda);
	}

}
