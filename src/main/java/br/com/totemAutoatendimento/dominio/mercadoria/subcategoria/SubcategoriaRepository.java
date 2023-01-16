package br.com.totemAutoatendimento.dominio.mercadoria.subcategoria;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;

public interface SubcategoriaRepository {

	Subcategoria criar(Subcategoria subcategoria);

	void remover(Subcategoria subcategoria);

	Subcategoria editar(Subcategoria subcategoriaAtualizada);
	
	Optional<Subcategoria> buscarPeloId(Long id);

	Optional<Subcategoria> buscarPeloNome(String nome);
	
	List<Subcategoria> buscarPelaCategoria(Categoria categoria);

	Page<Subcategoria> buscarTodas(Pageable paginacao);
}
