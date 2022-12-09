package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class UploadImagemDaSubcategoria {
    
    private SubcategoriaRepository repository;

    public UploadImagemDaSubcategoria(SubcategoriaRepository repository){
        this.repository = repository;
    }

    public void executar(Long id, MultipartFile file, String pathLocal, String urlServidor) {
        String extensao = file.getContentType();
        if(extensao ==  null || (!extensao.equals("image/jpeg") && !extensao.equals("image/png"))){
            throw new ViolacaoDeIntegridadeDeDadosException("O arquivo de imagem deve ser PNG ou JPG!");
        }
        BuscarSubcategoria buscarSubcategoria = new BuscarSubcategoria(repository);
        Subcategoria subcategoria = buscarSubcategoria.executar(id);
        try {
            Files.copy(file.getInputStream(), Path.of(pathLocal), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
        }
        subcategoria.setImagem(urlServidor);
        repository.editar(subcategoria);
    }
}
