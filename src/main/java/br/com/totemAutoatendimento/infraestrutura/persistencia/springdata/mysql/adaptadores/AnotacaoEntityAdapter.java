package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.AnotacaoRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.AnotacaoEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.AnotacaoEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.AnotacaoEntity;

@Repository
public class AnotacaoEntityAdapter implements AnotacaoRepository {

	@Autowired
	private AnotacaoEntityDao dao;

	@Autowired
	private AnotacaoEntityConverter anotacaoEntityConverter;
	
	@Override
	public Anotacao salvar(Anotacao anotacao) {
		AnotacaoEntity entity = dao.save(anotacaoEntityConverter.converterParaAnotacaoEntity(anotacao));
		return anotacaoEntityConverter.converterParaAnotacao(entity);
	}

	@Override
	public void deletar(Anotacao anotacao) {
		AnotacaoEntity entity = dao.save(anotacaoEntityConverter.converterParaAnotacaoEntity(anotacao));
		dao.delete(entity);
	}

	@Override
	public Optional<Anotacao> buscarPeloId(Long id) {
		Optional<AnotacaoEntity> entity = dao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(anotacaoEntityConverter.converterParaAnotacao(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Anotacao> buscarPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
		return dao.findAllByTimestampBetween(paginacao, 
				LocalDateTime.of(dataInicial, LocalTime.MIN),
				LocalDateTime.of(dataFinal, LocalTime.MAX))
				.map(anotacaoEntityConverter::converterParaAnotacao);
	}

	@Override
	public Page<Anotacao> buscarTodas(Pageable paginacao) {
		return dao.findAll(paginacao).map(anotacaoEntityConverter::converterParaAnotacao);
	}

}
