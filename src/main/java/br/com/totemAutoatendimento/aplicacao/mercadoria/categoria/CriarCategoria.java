package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class CriarCategoria {

	private CategoriaRepository repository;
	
	public CriarCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	public Categoria executar(String nome) {
		if(repository.buscarPorNome(nome).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Categoria com nome " + nome + " já cadastrada!");
		}
		Categoria categoria = new Categoria(null, nome, null);
		return repository.criar(categoria);
	}
}
