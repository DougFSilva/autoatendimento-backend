package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.avaliacao.Avaliacao;
import br.com.totemAutoatendimento.dominio.avaliacao.AvaliacaoRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.AvaliacaoEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.AvaliacaoEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.AvaliacaoEntity;

@Repository
public class AvaliacaoEntityAdapter implements AvaliacaoRepository{

	@Autowired
	private AvaliacaoEntityDao dao;
	
	@Autowired
	private AvaliacaoEntityConverter avaliacaoEntityConverter;

	@Override
	public Avaliacao salvar(Avaliacao avaliacao) {
		AvaliacaoEntity entity = avaliacaoEntityConverter.converterParaAvaliacaoEntity(avaliacao);
		return avaliacaoEntityConverter.converterParaAvaliacao(dao.save(entity));
	}

	@Override
	public void deletar(Avaliacao avaliacao) {
		AvaliacaoEntity entity = avaliacaoEntityConverter.converterParaAvaliacaoEntity(avaliacao);
		dao.delete(entity);
	}

	@Override
	public Optional<Avaliacao> buscarPeloId(Long id) {
		Optional<AvaliacaoEntity> entity = dao.findById(id);
		if(entity.isPresent()) {
			return Optional.of(avaliacaoEntityConverter.converterParaAvaliacao(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Avaliacao> buscarUltimaPeloTotem(Long totemId) {
		Optional<AvaliacaoEntity> entity = dao.findFirstByTotemIdOrderByTimestampDesc(totemId);
		if(entity.isPresent()) {
			return Optional.of(avaliacaoEntityConverter.converterParaAvaliacao(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Avaliacao> buscarTodasPelaData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal) {
		return dao.findAllByTimestampBetween(paginacao, dataInicial, dataFinal).map(avaliacaoEntityConverter::converterParaAvaliacao);
	}

	@Override
	public Page<Avaliacao> buscarTodas(Pageable paginacao) {
		return dao.findAll(paginacao).map(avaliacaoEntityConverter::converterParaAvaliacao);
	}
	
	
}
