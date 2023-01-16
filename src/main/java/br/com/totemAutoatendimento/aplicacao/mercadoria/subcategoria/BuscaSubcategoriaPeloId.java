package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscaSubcategoriaPeloId {

	private final SubcategoriaRepository repository;
	
	public BuscaSubcategoriaPeloId(SubcategoriaRepository repository) {
		this.repository = repository;
	}
	
	public Subcategoria buscar(Long id) {
		return repository.buscarPeloId(id).orElseThrow(
				() -> new ViolacaoDeIntegridadeDeDadosException("Subcategoria com id " + id + " não encontrada!"));
	}
}
