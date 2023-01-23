package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CriaSubcategoria {

	private final SubcategoriaRepository repository;

	private final CategoriaRepository categoriaRepository;
	
	private final SystemLogger logger;

	public CriaSubcategoria(SubcategoriaRepository repository, CategoriaRepository categoriaRepository, SystemLogger logger) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
		this.logger = logger;
	}
	
	public Subcategoria criar(Long categoriaId, String nome, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Categoria> categoria = categoriaRepository.buscarPeloId(categoriaId);
		if(categoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", categoriaId));
		}
		if (repository.buscarPeloNome(nome).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Subcategoria com nome %s já cadastrada!", nome));
		}
		Subcategoria subcategoriaCriada = repository.criar(new Subcategoria(null, categoria.get(), nome, "Sem imagem"));
		logger.info(
				String.format("Usuário %s - Subcategoria %s criada!", usuarioAutenticado.getRegistro(), subcategoriaCriada.getNome())
		);
		return subcategoriaCriada;
	}
}
