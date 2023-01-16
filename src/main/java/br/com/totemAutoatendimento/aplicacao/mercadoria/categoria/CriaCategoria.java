package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class CriaCategoria {

	private final CategoriaRepository repository;

	public CriaCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public Categoria criar(String nome) {
		if (repository.buscarPorNome(nome).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Categoria com nome " + nome + " já cadastrada!");
		}
		Categoria categoria = new Categoria(nome, "Sem imagem");
		return repository.criar(categoria);
	}
}
