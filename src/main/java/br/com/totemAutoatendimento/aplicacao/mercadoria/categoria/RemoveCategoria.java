package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
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
		BuscaCategoriaPeloId buscaCategoriaPeloId = new BuscaCategoriaPeloId(repository);
		Categoria categoria = buscaCategoriaPeloId.buscar(id);
		if (subcategoriaRepository.buscarPelaCategoria(categoria).size() > 0) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Impossível remover categoria pois existem subcategorias pertencentes a ela!");
		}
		repository.remover(categoria);
		logger.info(
				String.format("Usuário %s - Categoria %s removida!", usuarioAutenticado.getRegistro(), categoria.getNome())
		);
	}
}
