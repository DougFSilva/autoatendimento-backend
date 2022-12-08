package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

@Repository
public class CategoriaEntityRepository implements CategoriaRepository{
	
	@Autowired
	private CategoriaEntityJpaRepository repository;

	@Override
	public Categoria criar(Categoria categoria) {
		return repository.save(new CategoriaEntity(categoria)).converterParaCategoria();
	}

	@Override
	public void remover(Categoria categoria) {
		repository.delete(new CategoriaEntity(categoria));
	}

	@Override
	public Categoria editar(Categoria categoriaAtualizada) {
		return repository.save(new CategoriaEntity(categoriaAtualizada)).converterParaCategoria();
	}

	@Override
	public Optional<Categoria> buscar(Long id) {
		Optional<CategoriaEntity> entity = repository.findById(id);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaCategoria());
		}
		return Optional.empty();
	}

	@Override
	public Optional<Categoria> buscarPorNome(String nome) {
		Optional<CategoriaEntity> entity = repository.findByNome(nome);
		if(entity.isPresent()) {
			return Optional.of(entity.get().converterParaCategoria());
		}
		return Optional.empty();
	}

	@Override
	public Page<Categoria> buscarTodas(Pageable paginacao) {
		return repository.findAll(paginacao).map(CategoriaEntity::converterParaCategoria);
	}

}
