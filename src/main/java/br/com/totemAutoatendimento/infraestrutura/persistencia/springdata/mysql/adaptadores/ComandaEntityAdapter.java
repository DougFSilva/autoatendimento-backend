package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.ClienteEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.ComandaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.ComandaEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ComandaEntity;

@Repository
public class ComandaEntityAdapter implements ComandaRepository {

	@Autowired
	private ComandaEntityDao repository;

	@Autowired
	private ComandaEntityConverter comandaEntityConverter;

	@Autowired
	private ClienteEntityConverter clienteEntityConverter;

	@Override
	public Comanda criar(Comanda comanda) {
		ComandaEntity entity = repository.save(comandaEntityConverter.converterParaComandaEntity(comanda));
		return comandaEntityConverter.converterParaComanda(entity);
	}

	@Override
	public void remover(Comanda comanda) {
		repository.delete(comandaEntityConverter.converterParaComandaEntity(comanda));
	}

	@Override
	public Comanda editar(Comanda comandaAtualizada) {
		ComandaEntity entity = repository.save(comandaEntityConverter.converterParaComandaEntity(comandaAtualizada));
		return comandaEntityConverter.converterParaComanda(entity);
	}

	@Override
	public Optional<Comanda> buscarPeloId(Long id) {
		Optional<ComandaEntity> entity = repository.findById(id);
		if (entity.isPresent()) {
			return Optional.of(comandaEntityConverter.converterParaComanda(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Comanda> buscarPeloCartao(String cartao, Boolean aberta) {
		Optional<ComandaEntity> entity = repository.findByCartaoAndAberta(cartao, aberta);
		if (entity.isPresent()) {
			return Optional.of(comandaEntityConverter.converterParaComanda(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Comanda> buscarAbertas(Pageable paginacao, Boolean aberta) {
		return repository.findAllByAberta(paginacao, aberta).map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPeloCliente(Pageable paginacao, Cliente cliente) {
		return repository.findAllByCliente(paginacao, clienteEntityConverter.converterParaClienteEntity(cliente))
				.map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPelaData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return repository.findAllByAberturaBetween(paginacao, dataInicial, dataFinal)
				.map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPeloTipoDePagamento(Pageable paginacao, TipoPagamento tipoPagamento) {
		return repository.findAllByTipoPagamento(paginacao, tipoPagamento)
				.map(comandaEntityConverter::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarTodas(Pageable paginacao) {
		return repository.findAll(paginacao).map(comandaEntityConverter::converterParaComanda);
	}

}
