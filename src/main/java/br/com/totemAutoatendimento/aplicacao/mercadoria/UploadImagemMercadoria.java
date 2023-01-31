package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.aplicacao.imagem.SalvaImagem;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class UploadImagemMercadoria {

	private final MercadoriaRepository repository;

	private final StandardLogger logger;

	public UploadImagemMercadoria(MercadoriaRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public void executar(Long id, MultipartFile file, String pathPastaImagens, String baseUrlBuscarImagem,
			Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Mercadoria> mercadoria = repository.buscarPeloId(id);
		if (mercadoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Categoria com id %d não encontrada!", id));
		}
		SalvaImagem salvaImagem = new SalvaImagem();
		salvaImagem.salvar(file, Mercadoria.class, pathPastaImagens, usuarioAutenticado);
		mercadoria.get().setImagem(baseUrlBuscarImagem + file.getOriginalFilename());
		Mercadoria mercadoriaEditada = repository.salvar(mercadoria.get());
		logger.info(String.format("Inserido imagem à mercadoria com código %s!", mercadoriaEditada.getCodigo()), usuarioAutenticado);
	}
}
