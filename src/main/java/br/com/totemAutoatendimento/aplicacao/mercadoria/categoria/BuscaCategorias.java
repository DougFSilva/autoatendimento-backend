package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscaCategorias {

	private final CategoriaRepository repository;

	public BuscaCategorias(CategoriaRepository repository) {
		this.repository = repository;
	}

	public Categoria buscarPeloNome(String nome) {
		Optional<Categoria> categoria = repository.buscarPorNome(nome);
		return categoria
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Categoria com nome " + nome + " não encontrada!"));
	}

	public Page<Categoria> buscarTodas(Pageable paginacao) {
		return repository.buscarTodas(paginacao);
	}
}
