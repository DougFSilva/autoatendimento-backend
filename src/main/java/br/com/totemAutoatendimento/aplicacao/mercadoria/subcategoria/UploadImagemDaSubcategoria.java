package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.aplicacao.imagem.SalvaImagem;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class UploadImagemDaSubcategoria {

	private final SubcategoriaRepository repository;
	
	private final SystemLogger logger;

	public UploadImagemDaSubcategoria(SubcategoriaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public void executar(Long id, MultipartFile file, String pathPastaImagens, String baseUrlBuscarImagem, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
        Optional<Subcategoria> subcategoria = repository.buscarPeloId(id);
		if(subcategoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Subcategoria com id %d não encontrada!", id));
		}
		SalvaImagem salvaImagem = new SalvaImagem();
		salvaImagem.salvar(file, Subcategoria.class, pathPastaImagens, usuarioAutenticado);
		subcategoria.get().setImagem(baseUrlBuscarImagem + file.getOriginalFilename());
        Subcategoria subcategoriaEditada = repository.editar(subcategoria.get());
		logger.info(
				String.format("Usuário %s - Inserido imagem à subcategoria %s!", 
						usuarioAutenticado.getRegistro(), subcategoriaEditada.getNome())
		);
	}
}
