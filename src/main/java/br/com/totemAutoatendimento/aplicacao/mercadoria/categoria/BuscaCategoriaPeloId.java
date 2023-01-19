package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscaCategoriaPeloId {

	private final CategoriaRepository repository;

	public BuscaCategoriaPeloId(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public Categoria buscar(Long id) {
		Optional<Categoria> categoria = repository.buscarPeloId(id);
		return categoria.orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", id)));
	}
}
