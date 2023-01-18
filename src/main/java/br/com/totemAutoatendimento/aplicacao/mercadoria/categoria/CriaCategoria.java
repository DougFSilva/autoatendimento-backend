package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CriaCategoria {

	private final CategoriaRepository repository;
	
	private final SystemLogger logger;

	public CriaCategoria(CategoriaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	public Categoria criar(String nome, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		if (repository.buscarPorNome(nome).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Categoria com nome %s já cadastrada!", nome));
		}
		Categoria categoria = new Categoria(nome, "Sem imagem");
		Categoria categoriaCriada = repository.criar(categoria);
		logger.info(
				String.format("Usuário %s - Categoria %s criada!", usuarioAutenticado.getRegistro(), categoriaCriada.getNome())
		);
		return categoriaCriada;
	}
}
