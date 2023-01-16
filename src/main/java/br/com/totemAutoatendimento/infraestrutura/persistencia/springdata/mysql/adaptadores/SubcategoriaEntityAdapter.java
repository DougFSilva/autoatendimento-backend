package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.CategoriaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.SubcategoriaEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.SubcategoriaEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.SubcategoriaEntity;

@Repository
public class SubcategoriaEntityAdapter implements SubcategoriaRepository {

	@Autowired
	private SubcategoriaEntityDao repository;

	@Autowired
	private SubcategoriaEntityConverter subcategoriaEntityConverter;

	@Autowired
	private CategoriaEntityConverter categoriaEntityConverter;

	@Override
	public Subcategoria criar(Subcategoria subcategoria) {
		SubcategoriaEntity entity = repository
				.save(subcategoriaEntityConverter.converterParaSubcategoriaEntity(subcategoria));
		return subcategoriaEntityConverter.converterParaSubcategoria(entity);
	}

	@Override
	public void remover(Subcategoria subcategoria) {
		repository.delete(subcategoriaEntityConverter.converterParaSubcategoriaEntity(subcategoria));
	}

	@Override
	public Subcategoria editar(Subcategoria subcategoriaAtualizada) {
		SubcategoriaEntity entity = repository
				.save(subcategoriaEntityConverter.converterParaSubcategoriaEntity(subcategoriaAtualizada));
		return subcategoriaEntityConverter.converterParaSubcategoria(entity);
	}

	@Override
	public Optional<Subcategoria> buscarPeloId(Long id) {
		Optional<SubcategoriaEntity> entity = repository.findById(id);
		if (entity.isPresent()) {
			return Optional.of(subcategoriaEntityConverter.converterParaSubcategoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Subcategoria> buscarPelaCategoria(Categoria categoria) {
		return repository.findAllByCategoria(categoriaEntityConverter.converterParaCategoriaEntity(categoria))
				.stream()
				.map(subcategoriaEntityConverter::converterParaSubcategoria)
				.toList();
	}

	@Override
	public Optional<Subcategoria> buscarPeloNome(String nome) {
		Optional<SubcategoriaEntity> entity = repository.findByNome(nome);
		if (entity.isPresent()) {
			return Optional.of(subcategoriaEntityConverter.converterParaSubcategoria(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Subcategoria> buscarTodas() {
		return repository.findAll().stream().map(subcategoriaEntityConverter::converterParaSubcategoria).toList();
	}

}
