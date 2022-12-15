package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;

public class UploadImagemDaCategoria {
    
    private CategoriaRepository repository;

    public UploadImagemDaCategoria(CategoriaRepository repository){
        this.repository = repository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void executar(Long id, MultipartFile file, String pathLocal, String urlServidor) {
        String extensao = file.getContentType();
        if(extensao ==  null || (!extensao.equals("image/jpeg") && !extensao.equals("image/png"))){
            throw new ViolacaoDeIntegridadeDeDadosException("O arquivo de imagem deve ser PNG ou JPG!");
        }
        BuscarCategoria buscarCategoria = new BuscarCategoria(repository);
        Categoria categoria = buscarCategoria.executar(id);
        try {
            Files.copy(file.getInputStream(), Path.of(pathLocal), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
        }
        categoria.setImagem(urlServidor);
        repository.editar(categoria);
    }
}
