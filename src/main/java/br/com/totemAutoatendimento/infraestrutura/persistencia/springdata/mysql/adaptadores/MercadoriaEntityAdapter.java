package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasDeMaiorFaturamento;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasMaisVendidas;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.MercadoriaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.SubcategoriaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.MercadoriaEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.MercadoriaEntity;

@Repository
public class MercadoriaEntityAdapter implements MercadoriaRepository {

	@Autowired
	private MercadoriaEntityDao dao;

	@Autowired
	private MercadoriaEntityConverter mercadoriaEntityConverter;

	@Autowired
	private SubcategoriaEntityConverter subcategoriaEntityConverter;

	@Override
	public Mercadoria salvar(Mercadoria mercadoria) {
		MercadoriaEntity entity = dao.save(mercadoriaEntityConverter.converterParaMercadoriaEntity(mercadoria));
		return mercadoriaEntityConverter.converterParaMercadoria(entity);
	}

	@Override
	public void deletar(Mercadoria mercadoria) {
		dao.delete(mercadoriaEntityConverter.converterParaMercadoriaEntity(mercadoria));
	}

	@Override
	public Optional<Mercadoria> buscarPeloId(Long id) {
		Optional<MercadoriaEntity> entity = dao.findById(id);
		if (entity.isPresent()) {
			return Optional.of(mercadoriaEntityConverter.converterParaMercadoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Mercadoria> buscarPeloCodigo(String codigo) {
		Optional<MercadoriaEntity> entity = dao.findByCodigo(codigo);
		if (entity.isPresent()) {
			return Optional.of(mercadoriaEntityConverter.converterParaMercadoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Mercadoria> buscarPelaSubcategoria(Pageable paginacao, Subcategoria subcatergoria) {
		return dao
				.findAllBySubcategoria(paginacao,
						subcategoriaEntityConverter.converterParaSubcategoriaEntity(subcatergoria))
				.map(mercadoriaEntityConverter::converterParaMercadoria);
	}

	@Override
	public Page<Mercadoria> buscarEmPromocao(Pageable paginacao, Boolean promocao) {
		return dao.findAllByPromocao(paginacao, promocao)
				.map(mercadoriaEntityConverter::converterParaMercadoria);
	}

	@Override
	public Page<Mercadoria> buscarTodas(Pageable paginacao) {
		return dao.findAll(paginacao).map(mercadoriaEntityConverter::converterParaMercadoria);
	}
	
	@Override
	public Page<RelatorioMercadoriasMaisVendidas> buscarMaisVendidasPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
		return dao.buscarMaisVendidasPelaData(paginacao, dataInicial, dataFinal);
	}

	@Override
	public Page<RelatorioMercadoriasDeMaiorFaturamento> buscarComMaiorFaturamentoPelaData(Pageable paginacao,
			LocalDate dataInicial, LocalDate dataFinal) {
		return dao.buscarComMaiorFaturamentoPelaData(paginacao, dataInicial, dataFinal);
	}



}
