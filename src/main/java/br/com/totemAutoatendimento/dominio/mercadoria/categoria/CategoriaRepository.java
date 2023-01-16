package br.com.totemAutoatendimento.dominio.mercadoria.categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

	Categoria criar(Categoria categoria);
	
	void remover(Categoria categoria);
	
	Categoria editar(Categoria categoriaAtualizada);
	
	Optional<Categoria> buscarPeloId(Long id);
	
	Optional<Categoria> buscarPorNome(String nome);
	
	List<Categoria> buscarTodas();
	
}
