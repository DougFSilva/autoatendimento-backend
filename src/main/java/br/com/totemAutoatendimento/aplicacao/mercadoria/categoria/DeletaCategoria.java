package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveCategoria {

	private final CategoriaRepository repository;
	
	private final SubcategoriaRepository subcategoriaRepository;
	
	private final SystemLogger logger;


	public RemoveCategoria(CategoriaRepository repository, SubcategoriaRepository subcategoriaRepository, SystemLogger logger) {
		this.repository = repository;
		this.subcategoriaRepository = subcategoriaRepository;
		this.logger = logger;
	}

	public void remover(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Categoria> categoria = repository.buscarPeloId(id);
		if(categoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", id));
		}
		if (subcategoriaRepository.buscarPelaCategoria(categoria.get()).size() > 0) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Impossível remover categoria pois existem subcategorias pertencentes a ela!");
		}
		repository.remover(categoria.get());
		logger.info(
				String.format("Usuário %s - Categoria %s removida!", 
						usuarioAutenticado.getRegistro(), 
						categoria.get().getNome())
		);
	}
}
