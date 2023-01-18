package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class UploadImagemDaCategoria {
    
	private final CategoriaRepository repository;

	private final SystemLogger logger;

	public UploadImagemDaCategoria(CategoriaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

    public void executar(Long id, MultipartFile file, String pathLocal, String urlServidor, Usuario usuarioAutenticado) {
    	AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
        String extensao = file.getContentType();
        if(extensao ==  null || (!extensao.equals("image/jpeg") && !extensao.equals("image/png"))){
            throw new ViolacaoDeIntegridadeDeDadosException("O arquivo de imagem deve ser PNG ou JPG!");
        }
        BuscaCategoriaPeloId buscarCategoriaPeloId = new BuscaCategoriaPeloId(repository);
        Categoria categoria = buscarCategoriaPeloId.buscar(id);
        try {
            Files.copy(file.getInputStream(), Path.of(pathLocal), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
        }
        categoria.setImagem(urlServidor);
        Categoria categoriaEditada = repository.editar(categoria);
        logger.info(
        		String.format("Usuário %s - Inserido imagem à categoria %s!", 
        				usuarioAutenticado.getRegistro(), categoriaEditada.getNome())
        );
    }
}
