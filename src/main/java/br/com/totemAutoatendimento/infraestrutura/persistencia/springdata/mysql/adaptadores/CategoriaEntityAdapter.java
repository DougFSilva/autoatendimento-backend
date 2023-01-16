package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.CategoriaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.CategoriaEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CategoriaEntity;

@Repository
public class CategoriaEntityAdapter implements CategoriaRepository{
	
	@Autowired
	private CategoriaEntityDao repository;
	
	@Autowired
	private CategoriaEntityConverter categoriaEntityConverter;

	@Override
	public Categoria criar(Categoria categoria) {
		CategoriaEntity entity = repository.save(categoriaEntityConverter.converterParaCategoriaEntity(categoria));
		return categoriaEntityConverter.converterParaCategoria(entity);
	}

	@Override
	public void remover(Categoria categoria) {
		repository.delete(categoriaEntityConverter.converterParaCategoriaEntity(categoria));
	}

	@Override
	public Categoria editar(Categoria categoriaAtualizada) {
		CategoriaEntity entity = repository.save(categoriaEntityConverter.converterParaCategoriaEntity(categoriaAtualizada));
		return categoriaEntityConverter.converterParaCategoria(entity);
	}

	@Override
	public Optional<Categoria> buscarPeloId(Long id) {
		Optional<CategoriaEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return Optional.of(categoriaEntityConverter.converterParaCategoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Categoria> buscarPorNome(String nome) {
		Optional<CategoriaEntity> entity = repository.findByNome(nome);
		if(entity.isPresent()) {
			return Optional.of(categoriaEntityConverter.converterParaCategoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable paginacao) {
		return repository.findAll(paginacao).map(categoriaEntityConverter::converterParaCategoria);
	}

}
