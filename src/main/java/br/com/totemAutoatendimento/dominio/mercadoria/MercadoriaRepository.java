package br.com.totemAutoatendimento.dominio.mercadoria;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;

public interface MercadoriaRepository {

	Mercadoria criar(Mercadoria mercadoria);

	void remover(Mercadoria mercadoria);

	Mercadoria editar(Mercadoria mercadoriaAtualizada);

	Optional<Mercadoria> buscarPeloId(Long id);

	Optional<Mercadoria> buscarPeloCodigo(String codigo);

	Page<Mercadoria> buscarPelaSubcategoria(Pageable paginacao, Subcategoria subcategoria);

	Page<Mercadoria> buscarEmPromocao(Pageable paginacao, Boolean promocao);

	Page<Mercadoria> buscarTodas(Pageable paginacao);

}
