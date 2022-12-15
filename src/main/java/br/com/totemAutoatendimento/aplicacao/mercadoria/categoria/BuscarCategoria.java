package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscarCategoria {

	private CategoriaRepository repository;

	public BuscarCategoria(CategoriaRepository repository) {
		this.repository = repository;
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Categoria executar(Long id) {
		return repository.buscar(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Categoria com id " + id + " não encontrada!"));
	}

}
