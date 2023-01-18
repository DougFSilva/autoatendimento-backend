package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class UploadImagemMercadoria {

    private final MercadoriaRepository repository;
    
    private final SystemLogger logger;

    public UploadImagemMercadoria(MercadoriaRepository repository, SystemLogger logger) {
        this.repository = repository;
        this.logger = logger;
    }

    public void executar(Long id, MultipartFile file, String pathLocal, String urlServidor, Usuario usuarioAutenticado) {
    	AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
        String extensao = file.getContentType();
        if(extensao ==  null || (!extensao.equals("image/jpeg") && !extensao.equals("image/png"))){
            throw new ViolacaoDeIntegridadeDeDadosException("O arquivo de imagem deve ser PNG ou JPG!");
        }
        BuscaMercadoriaPeloId buscaMercadoriaPeloId = new BuscaMercadoriaPeloId(repository);
        Mercadoria mercadoria = buscaMercadoriaPeloId.buscar(id);
        try {
            Files.copy(file.getInputStream(), Path.of(pathLocal), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
        }
        mercadoria.setImagem(urlServidor);
        Mercadoria mercadoriaEditada = repository.editar(mercadoria);
        logger.info(
        		String.format("Usuário %s - Inserido imagem à mercadoria com código %s!", 
        				usuarioAutenticado.getRegistro(), mercadoriaEditada.getCodigo())
        );
    }
}
