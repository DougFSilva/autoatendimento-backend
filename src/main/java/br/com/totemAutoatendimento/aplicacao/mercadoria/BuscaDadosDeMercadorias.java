package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscaSubcategoriaPeloId;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class BuscaDadosDeMercadorias {

	private final MercadoriaRepository repository;

	private final SubcategoriaRepository subcategoriaRepository;

	public BuscaDadosDeMercadorias(MercadoriaRepository repository, SubcategoriaRepository subcategoriaRepository) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
	}

	public DadosDeMercadoria buscarPeloId(Long id) {
		BuscaMercadoriaPeloId buscaMercadoriaPeloId = new BuscaMercadoriaPeloId(repository);
		return new DadosDeMercadoria(buscaMercadoriaPeloId.buscar(id));
	}

	public DadosDeMercadoria buscarPeloCodigo(String codigo) {
		Optional<Mercadoria> mercadoria = repository.buscarPeloCodigo(codigo);
		if (mercadoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Mercadoria com código %s não encontrada!", codigo));
		}
		return new DadosDeMercadoria(mercadoria.get());
	}

	public Page<DadosDeMercadoria> buscarEmPromocao(Pageable paginacao, Boolean promocao) {
		return repository.buscarEmPromocao(paginacao, promocao).map(DadosDeMercadoria::new);
	}

	public Page<DadosDeMercadoria> buscarPelaSubcategoria(Pageable paginacao, Long idSubcategoria) {
		BuscaSubcategoriaPeloId buscaSubcategoriaPeloId = new BuscaSubcategoriaPeloId(subcategoriaRepository);
		Subcategoria subcategoria = buscaSubcategoriaPeloId.buscar(idSubcategoria);
		return repository.buscarPelaSubcategoria(paginacao, subcategoria).map(DadosDeMercadoria::new);
	}

	public Page<DadosDeMercadoria> buscarTodas(Pageable paginacao) {
		return repository.buscarTodas(paginacao).map(DadosDeMercadoria::new);
	}
}
