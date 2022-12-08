package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class RemoverCategoria {

	private CategoriaRepository repository;

	private MercadoriaRepository mercadoriaRepository;

	public RemoverCategoria(CategoriaRepository repository, MercadoriaRepository mercadoriaRepository) {
		this.repository = repository;
		this.mercadoriaRepository = mercadoriaRepository;
	}

	public void executar(Long id) {
		BuscarCategoria buscarCategoria = new BuscarCategoria(repository);
		Categoria categoria = buscarCategoria.executar(id);
		int quantidadeDeMercadoria = mercadoriaRepository.buscarPorCategoria(Pageable.unpaged(), categoria).getContent()
				.size();
		if (quantidadeDeMercadoria > 0) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Impossível remover categoria pois existem " + quantidadeDeMercadoria + " mercadorias pertencentes a ela!");
		}
		repository.remover(categoria);
	}
}
