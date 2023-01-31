package br.com.totemAutoatendimento.dominio.mercadoria.subcategoria;

import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;

public interface SubcategoriaRepository {

	Subcategoria salvar(Subcategoria subcategoria);

	void deletar(Subcategoria subcategoria);

	Optional<Subcategoria> buscarPeloId(Long id);

	Optional<Subcategoria> buscarPeloNome(String nome);
	
	List<Subcategoria> buscarPelaCategoria(Categoria categoria);

	List<Subcategoria> buscarTodas();
}
