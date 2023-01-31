package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.aplicacao.imagem.SalvaImagem;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class UploadImagemDaCategoria {

	private final CategoriaRepository repository;

	private final StandardLogger logger;

	public UploadImagemDaCategoria(CategoriaRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public void upload(Long id, MultipartFile file, String pathPastaImagens, String baseUrlBuscarImagem,
			Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Categoria> categoria = repository.buscarPeloId(id);
		if (categoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", id));
		}
		SalvaImagem salvaImagem = new SalvaImagem();
		salvaImagem.salvar(file, Categoria.class, pathPastaImagens, usuarioAutenticado);
		categoria.get().setImagem(baseUrlBuscarImagem + file.getOriginalFilename());
		Categoria categoriaEditada = repository.salvar(categoria.get());
		logger.info(String.format("Inserido imagem à categoria %s!", categoriaEditada.getNome()), usuarioAutenticado);
	}
}
