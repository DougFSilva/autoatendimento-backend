package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class CriarCategoria {

	private CategoriaRepository repository;
	
	public CriarCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public Categoria executar(String nome) {
		if(repository.buscarCategoriaPorNome(nome).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Categoria com nome " + nome + " já cadastrada!");
		}
		Categoria categoria = new Categoria(null, nome);
		return repository.criar(categoria);
	}
}
