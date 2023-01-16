package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.List;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaCategoriaPeloId;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscaSubcategorias {

	private final SubcategoriaRepository repository;

	private final CategoriaRepository categoriaRepository;

	public BuscaSubcategorias(SubcategoriaRepository repository, CategoriaRepository categoriaRepository) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}

	public List<Subcategoria> buscarPelaCategoria(Long categoriaId) {
		BuscaCategoriaPeloId buscaCategoriaPeloId = new BuscaCategoriaPeloId(categoriaRepository);
		Categoria categoria = buscaCategoriaPeloId.buscar(categoriaId);
		return repository.buscarPelaCategoria(categoria);
	}

	public List<Subcategoria> buscarTodas() {
		return repository.buscarTodas();
	}
}
