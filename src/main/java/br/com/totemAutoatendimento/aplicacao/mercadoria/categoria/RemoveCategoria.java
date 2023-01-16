package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class RemoveCategoria {

	private final CategoriaRepository repository;
	
	private final SubcategoriaRepository subcategoriaRepository;

	public RemoveCategoria(CategoriaRepository repository, SubcategoriaRepository subcategoriaRepository) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
	}

	public void remover(Long id) {
		BuscaCategoriaPeloId buscaCategoriaPeloId = new BuscaCategoriaPeloId(repository);
		Categoria categoria = buscaCategoriaPeloId.buscar(id);
		if (subcategoriaRepository.buscarPelaCategoria(categoria).size() > 0) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Impossível remover categoria pois existem subcategorias pertencentes a ela!");
		}
		repository.remover(categoria);
	}
}
