package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente.ClienteEntity;

@Repository
public class ComandaEntityRepository implements ComandaRepository {

	@Autowired
	private ComandaEntityJpaRepository repository;

	@Override
	public Comanda criar(Comanda comanda) {
		return repository.save(new ComandaEntity(comanda)).converterParaComanda();
	}

	@Override
	public void remover(Comanda comanda) {
		repository.delete(new ComandaEntity(comanda));
	}

	@Override
	public Comanda editar(Comanda comandaAtualizada) {
		return repository.save(new ComandaEntity(comandaAtualizada)).converterParaComanda();
	}

	@Override
	public Optional<Comanda> buscar(Long id) {
		Optional<ComandaEntity> entity = repository.findById(id);
		if (entity.isPresent()) {
			return Optional.of(entity.get().converterParaComanda());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Comanda> buscarPorCartao(String cartao, Boolean aberta) {
		Optional<ComandaEntity> entity = repository.findByCartaoAndAberta(cartao, aberta);
		if (entity.isPresent()) {
			return Optional.of(entity.get().converterParaComanda());
		}
		return Optional.empty();
	}

	@Override
	public Page<Comanda> buscarAbertas(Pageable paginacao, Boolean aberta) {
		return repository.findAllByAberta(paginacao, aberta).map(ComandaEntity::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPorCliente(Pageable paginacao, Cliente cliente) {
		return repository.findAllByCliente(paginacao, new ClienteEntity(cliente))
				.map(ComandaEntity::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPorData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return repository.findAllByAberturaBetween(paginacao, dataInicial, dataFinal)
				.map(ComandaEntity::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarPorTipoDePagamento(Pageable paginacao, TipoPagamento tipoPagamento) {
		return repository.findAllByTipoPagamento(paginacao, tipoPagamento)
				.map(ComandaEntity::converterParaComanda);
	}

	@Override
	public Page<Comanda> buscarTodas(Pageable paginacao) {
		return repository.findAll(paginacao).map(ComandaEntity::converterParaComanda);
	}

}
