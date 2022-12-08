package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class UploadImagemDeMercadoria {

    private MercadoriaRepository repository;

    public UploadImagemDeMercadoria(MercadoriaRepository repository) {
        this.repository = repository;
    }

    public void executar(Long id, MultipartFile arquivo, String path, String nome) {
        BuscarMercadoria buscarMercadoria = new BuscarMercadoria(repository);
        System.out.println("===== UploadImagem linha 24: " + arquivo.getContentType());
        System.out.println("===== UploadImagem linha 23: " + Path.of(path, nome));
        try {
            Files.copy(arquivo.getInputStream(), Path.of(path, nome), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
        }
        Mercadoria mercadoria = buscarMercadoria.executar(id);
        mercadoria.setImagem(path + nome);
        repository.criar(mercadoria);
    }
}
