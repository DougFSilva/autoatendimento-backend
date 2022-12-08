package br.com.totemAutoatendimento.dominio.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubcategoriaRepository {

	Subcategoria criar(Subcategoria subcategoria);

	void remover(Subcategoria subcategoria);

	Subcategoria editar(Subcategoria subcategoriaAtualizada);

	Optional<Subcategoria> buscar(Long id);

	Optional<Subcategoria> buscarPorNome(String nome);

	Page<Subcategoria> buscarTodas(Pageable paginacao);
}
