package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaCategoria {

	private final CategoriaRepository repository;

	private final SubcategoriaRepository subcategoriaRepository;

	private final StandardLogger logger;

	public DeletaCategoria(CategoriaRepository repository, SubcategoriaRepository subcategoriaRepository,
			StandardLogger logger) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
		this.logger = logger;
	}

	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Categoria> categoria = repository.buscarPeloId(id);
		if (categoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", id));
		}
		if (subcategoriaRepository.buscarPelaCategoria(categoria.get()).size() > 0) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Impossível remover categoria pois existem subcategorias pertencentes a ela!");
		}
		repository.deletar(categoria.get());
		logger.info(String.format("Categoria %s deletada!", categoria.get().getNome()), usuarioAutenticado);
	}
}
