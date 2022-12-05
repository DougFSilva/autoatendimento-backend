package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class RemoverCategoria {

private CategoriaRepository repository;
	
	public RemoverCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public void executar(Long id) {
		BuscarCategoria buscarCategoria = new BuscarCategoria(repository);
		Categoria categoria = buscarCategoria.executar(id);
		repository.remover(categoria);
	}
}
