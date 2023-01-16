package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosEditarMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscaSubcategoriaPeloId;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class EditaMercadoria {

	private final MercadoriaRepository repository;

	private final SubcategoriaRepository subcategoriaRepository;

	public EditaMercadoria(MercadoriaRepository repository, SubcategoriaRepository subcategoriaRepository) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
	}

	@Transactional
	public DadosDeMercadoria editar(DadosEditarMercadoria dados) {
		Optional<Mercadoria> mercadoriaPorCodigo = repository.buscarPeloCodigo(dados.codigo());
		if (mercadoriaPorCodigo.isPresent() && mercadoriaPorCodigo.get().getId() != dados.id()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Mercadoria com código " + dados.codigo() + " já cadastrada!");
		}
		BuscaMercadoriaPeloId buscarMercadoriaPeloId = new BuscaMercadoriaPeloId(repository);
		BuscaSubcategoriaPeloId buscaSubcategoriaPeloId = new BuscaSubcategoriaPeloId(subcategoriaRepository);
		Mercadoria mercadoria = buscarMercadoriaPeloId.buscar(dados.id());
		Subcategoria subcategoria = buscaSubcategoriaPeloId.buscar(dados.idSubcategoria());
		mercadoria.setCodigo(dados.codigo());
		mercadoria.setSubcategoria(subcategoria);
		mercadoria.setDescricao(dados.descricao());
		mercadoria.setPreco(dados.preco());
		mercadoria.setPromocao(dados.promocao());
		mercadoria.setPrecoPromocional(dados.precoPromocional());
		mercadoria.setDisponivel(dados.disponivel());
		return new DadosDeMercadoria(repository.editar(mercadoria));
	}
}
