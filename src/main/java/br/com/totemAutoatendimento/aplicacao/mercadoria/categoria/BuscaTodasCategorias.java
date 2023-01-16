package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.List;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscaTodasCategorias {

	private final CategoriaRepository repository;

	public BuscaTodasCategorias(CategoriaRepository repository) {
		this.repository = repository;
	}

	public List<Categoria> buscar() {
		return repository.buscarTodas();
	}
}
