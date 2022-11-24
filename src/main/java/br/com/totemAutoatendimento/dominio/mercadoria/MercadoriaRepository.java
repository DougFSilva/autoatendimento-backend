package br.com.totemAutoatendimento.dominio.mercadoria;

import java.util.List;

public interface MercadoriaRepository {

	Mercadoria criar(Mercadoria mercadoria);
	
	void remover(Long id);
	
	Mercadoria editar(Long id, Mercadoria mercadoriaAtualizada);
	
	Mercadoria buscar(Long id);
	
	List<Mercadoria> buscarPorCategoria(Categoria categoria);
	
	List<Mercadoria> buscarPorSubcategoria(Subcategoria subcatergoria);
	
	List<Mercadoria> buscarPorPromocao(Boolean promocao);
	
	List<Mercadoria> buscarTodas();
}
