package br.com.totemAutoatendimento.dominio.mercadoria.categoria;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaRepository {

	Categoria criar(Categoria categoria);
	
	void remover(Categoria categoria);
	
	Categoria editar(Categoria categoriaAtualizada);
	
	Optional<Categoria> buscarPeloId(Long id);
	
	Optional<Categoria> buscarPorNome(String nome);
	
	Page<Categoria> buscarTodas(Pageable paginacao);
	
}
