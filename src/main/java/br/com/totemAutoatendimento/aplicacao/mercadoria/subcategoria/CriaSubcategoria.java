package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaCategoriaPeloId;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class CriaSubcategoria {

	private final SubcategoriaRepository repository;

	private final CategoriaRepository categoriaRepository;

	public CriaSubcategoria(SubcategoriaRepository repository, CategoriaRepository categoriaRepository) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}
	
	public Subcategoria criar(Long categoriaId, String nome) {
		BuscaCategoriaPeloId buscaCategoriaPeloId = new BuscaCategoriaPeloId(categoriaRepository);
		Categoria categoria = buscaCategoriaPeloId.buscar(categoriaId);
		if (repository.buscarPeloNome(nome).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Subcategoria com nome " + nome + " já cadastrada!");
		}
		return repository.criar(new Subcategoria(null, categoria, nome, "Sem imagem"));
	}
}
