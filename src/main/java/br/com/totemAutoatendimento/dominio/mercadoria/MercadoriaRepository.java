package br.com.totemAutoatendimento.dominio.mercadoria;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;

public interface MercadoriaRepository {

	Mercadoria criar(Mercadoria mercadoria);
	
	void remover(Mercadoria mercadoria);
	
	Mercadoria editar(Mercadoria mercadoriaAtualizada);
	
	Optional<Mercadoria> buscar(Long id);

	Optional<Mercadoria> buscarPorCodigo(String codigo);
	
	Page<Mercadoria> buscarPorCategoria(Pageable paginacao, Categoria categoria);
	
	Page<Mercadoria> buscarPorSubcategoria(Pageable paginacao, Subcategoria subcatergoria);
	
	Page<Mercadoria> buscarEmPromocao(Pageable paginacao, Boolean promocao);
	
	Page<Mercadoria> buscarTodas(Pageable paginacao);
}
