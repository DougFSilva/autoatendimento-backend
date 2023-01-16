package br.com.totemAutoatendimento.aplicacao.mercadoria;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.DadosCriarMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscaSubcategoriaPeloId;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

@PreAuthorize("hasRole('ADMIN')")
public class CriaMercadoria {

	private final MercadoriaRepository repository;

	private final SubcategoriaRepository subcategoriaRepository;

	public CriaMercadoria(MercadoriaRepository repository, SubcategoriaRepository subcategoriaRepository) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
	}

	@Transactional
	public Mercadoria criar(DadosCriarMercadoria dados) {
		if (repository.buscarPeloCodigo(dados.codigo()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Mercadoria com código " + dados.codigo() + " já cadastrada!");
		}
		BuscaSubcategoriaPeloId buscaSubcategoriaPeloId = new BuscaSubcategoriaPeloId(subcategoriaRepository);
		Subcategoria subcategoria = buscaSubcategoriaPeloId.buscar(dados.subcategoriaId());
		Mercadoria mercadoria = new Mercadoria(null, dados.codigo(), subcategoria, dados.descricao(), dados.preco(),
				dados.promocao(), dados.precoPromocional(), true, "Sem imagem");
		return repository.criar(mercadoria);
	}

}
