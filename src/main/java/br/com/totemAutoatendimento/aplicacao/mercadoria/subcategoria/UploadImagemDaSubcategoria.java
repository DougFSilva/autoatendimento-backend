package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;

public class UploadImagemDaSubcategoria {
    
    private SubcategoriaRepository repository;

    public UploadImagemDaSubcategoria(SubcategoriaRepository repository){
        this.repository = repository;
    }

    public void executar(Long id, MultipartFile file, String pathLocal, String nome) {
        String extensao = file.getContentType();
        if(extensao ==  null || (!extensao.equals("image/jpeg") && !extensao.equals("image/png"))){
            throw new ViolacaoDeIntegridadeDeDadosException("O arquivo de imagem deve ser PNG ou JPG!");
        }
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        BuscarSubcategoria buscarSubcategoria = new BuscarSubcategoria(repository);
        Subcategoria subcategoria = buscarSubcategoria.executar(id);
        try {
            Files.copy(file.getInputStream(), Path.of(pathLocal + "subcategoria/", nome), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
        }
        subcategoria.setImagem(url + "/mercadoria/subcategoria/imagem/" + nome);
        repository.editar(subcategoria);
    }
}
