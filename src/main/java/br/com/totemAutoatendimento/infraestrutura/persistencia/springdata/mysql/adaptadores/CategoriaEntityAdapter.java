package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.CategoriaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.CategoriaEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CategoriaEntity;

@Repository
public class CategoriaEntityAdapter implements CategoriaRepository{
	
	@Autowired
	private CategoriaEntityDao dao;
	
	@Autowired
	private CategoriaEntityConverter categoriaEntityConverter;

	@Override
	public Categoria salvar(Categoria categoria) {
		CategoriaEntity entity = dao.save(categoriaEntityConverter.converterParaCategoriaEntity(categoria));
		return categoriaEntityConverter.converterParaCategoria(entity);
	}

	@Override
	public void deletar(Categoria categoria) {
		dao.delete(categoriaEntityConverter.converterParaCategoriaEntity(categoria));
	}

	@Override
	public Optional<Categoria> buscarPeloId(Long id) {
		Optional<CategoriaEntity> entity = dao.findById(id);
		if(entity.isPresent()) {
			return Optional.of(categoriaEntityConverter.converterParaCategoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Categoria> buscarPorNome(String nome) {
		Optional<CategoriaEntity> entity = dao.findByNome(nome);
		if(entity.isPresent()) {
			return Optional.of(categoriaEntityConverter.converterParaCategoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Categoria> buscarTodas() {
		return dao.findAll().stream().map(categoriaEntityConverter::converterParaCategoria).toList();
	}

}
