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
	
	public Categoria editar(Categoria categoriaAtualizada) {
		BuscaCategoriaPeloId buscarCategoriaPeloId = new BuscaCategoriaPeloId(repository);
		Categoria categoria = buscarCategoriaPeloId.buscar(categoriaAtualizada.getId());
		Optional<Categoria> categoriaPorNome = repository.buscarPorNome(categoriaAtualizada.getNome());
		if (categoriaPorNome.isPresent() && categoriaPorNome.get().getId() != categoriaAtualizada.getId()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Categoria com nome " + categoriaAtualizada.getNome() + " já cadastrada!");
		}
		categoria.setNome(categoriaAtualizada.getNome());
		return repository.criar(categoria);
	}
}
