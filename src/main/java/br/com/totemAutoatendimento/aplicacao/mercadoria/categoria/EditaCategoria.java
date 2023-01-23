package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EditaCategoria {

	private final CategoriaRepository repository;

	private final SystemLogger logger;

	public EditaCategoria(CategoriaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public Categoria editar(Long id, String nome, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Categoria> categoria = repository.buscarPeloId(id);
		if(categoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", id));
		}
		Optional<Categoria> categoriaPorNome = repository.buscarPorNome(nome);
		if (categoriaPorNome.isPresent() && categoriaPorNome.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Categoria com nome %s já cadastrada!", nome));
		}
		categoria.get().setNome(nome);
		Categoria categoriaEditada = repository.editar(categoria.get());
		logger.info(
				String.format("Usuário %s - Editada categoria de id %d!", usuarioAutenticado.getRegistro(), categoriaEditada.getId())
		);
		return categoriaEditada;
	}
}
