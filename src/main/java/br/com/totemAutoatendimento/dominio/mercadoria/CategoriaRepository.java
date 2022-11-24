package br.com.totemAutoatendimento.dominio.mercadoria;

import java.util.List;

public interface CategoriaRepository {

	Categoria criar(Categoria categoria);
	
	void remover(Long id);
	
	Categoria editar(Long id, Categoria categoriaAtualizada);
	
	Categoria buscar(Long id);
	
	List<Categoria> buscarTodas();
	
}
