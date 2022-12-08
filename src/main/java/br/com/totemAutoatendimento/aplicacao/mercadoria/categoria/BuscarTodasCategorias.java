package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class BuscarTodasCategorias {

	private CategoriaRepository repository;

	public BuscarTodasCategorias(CategoriaRepository repository) {
		this.repository = repository;
	}
	
	public Page<Categoria> executar(Pageable paginacao){
		return repository.buscarTodas(paginacao);
	}
}
