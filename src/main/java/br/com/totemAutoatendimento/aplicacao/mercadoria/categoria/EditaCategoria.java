package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class EditaCategoria {

	private final CategoriaRepository repository;

	public EditaCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public Categoria editar(Long id, String nome) {
		BuscaCategoriaPeloId buscarCategoriaPeloId = new BuscaCategoriaPeloId(repository);
		Categoria categoria = buscarCategoriaPeloId.buscar(id);
		Optional<Categoria> categoriaPorNome = repository.buscarPorNome(nome);
		if (categoriaPorNome.isPresent() && categoriaPorNome.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Categoria com nome " + nome + " já cadastrada!");
		}
		categoria.setNome(nome);
		return repository.criar(categoria);
	}
}
