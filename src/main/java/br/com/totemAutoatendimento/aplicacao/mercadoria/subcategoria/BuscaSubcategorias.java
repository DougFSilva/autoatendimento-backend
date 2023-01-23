package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosDeSubcategoria;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaSubcategorias {

	private final SubcategoriaRepository repository;

	private final CategoriaRepository categoriaRepository;

	public BuscaSubcategorias(SubcategoriaRepository repository, CategoriaRepository categoriaRepository) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
	}

	public List<DadosDeSubcategoria> buscarPelaCategoria(Long categoriaId, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Categoria> categoria = categoriaRepository.buscarPeloId(categoriaId);
		if(categoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", categoriaId));
		}
		List<Subcategoria> subcategorias = repository.buscarPelaCategoria(categoria.get());
		return subcategorias.stream().map(DadosDeSubcategoria::new).toList();
	}

	public List<Subcategoria> buscarTodas() {
		return repository.buscarTodas();
	}
}
