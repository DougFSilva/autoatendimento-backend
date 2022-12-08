package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class UploadImagemDeMercadoria {

    private MercadoriaRepository repository;

    public UploadImagemDeMercadoria(MercadoriaRepository repository) {
        this.repository = repository;
    }

    public void executar(Long id, MultipartFile file, String pathLocal, String nome) {
        String extensao = file.getContentType();
        if(extensao ==  null || (!extensao.equals("image/jpeg") && !extensao.equals("image/png"))){
            throw new ViolacaoDeIntegridadeDeDadosException("O arquivo de imagem deve ser PNG ou JPG!");
        }
        String url = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
        BuscarMercadoria buscarMercadoria = new BuscarMercadoria(repository);
        try {
            Files.copy(file.getInputStream(), Path.of(pathLocal + "mercadoria/", nome), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
        }
        Mercadoria mercadoria = buscarMercadoria.executar(id);
        mercadoria.setImagem(url + "/mercadoria/imagem/" + nome);
        repository.criar(mercadoria);
    }
}
