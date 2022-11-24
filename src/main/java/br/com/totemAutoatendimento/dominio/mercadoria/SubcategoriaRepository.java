package br.com.totemAutoatendimento.dominio.mercadoria;

import java.util.List;

public interface SubcategoriaRepository {

	Subcategoria criar(Subcategoria subcategoria);

	void remover(Long id);

	Subcategoria editar(Long id, Subcategoria subcategoriaAtualizada);

	Subcategoria buscar(Long id);

	List<Subcategoria> buscarTodas();
}
