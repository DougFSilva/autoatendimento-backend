package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class EditarCategoria {

	private CategoriaRepository repository;

	public EditarCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}

	@PreAuthorize("hasRole('ADMIN')")
	public Categoria executar(Categoria categoriaAtualizada) {
		BuscarCategoria buscarCategoria = new BuscarCategoria(repository);
		Categoria categoria = buscarCategoria.executar(categoriaAtualizada.getId());
		Optional<Categoria> categoriaPorNome = repository.buscarPorNome(categoriaAtualizada.getNome());
		if (categoriaPorNome.isPresent() && categoriaPorNome.get().getId() != categoriaAtualizada.getId()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Categoria com nome " + categoriaAtualizada.getNome() + " já cadastrada!");
		}
		categoria.setNome(categoriaAtualizada.getNome());
		return repository.criar(categoria);
	}
}
