package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.List;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscarTodasCategorias {

	private CategoriaRepository repository;

	public BuscarTodasCategorias(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public List<Categoria> executar(){
		return repository.buscarTodas();
	}
}
