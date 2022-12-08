package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.totemAutoatendimento.dominio.exception.ArquivoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ErroNaConversaoDaImagemEmByteArrayException;

public class BuscarImagem {

    public byte[] executar(String path) {
        System.out.println("====="+ path);
        File file = new File(path);
        byte[] imagem = new byte[(int) file.length()];
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileInputStream.read(imagem);
                fileInputStream.close();
            } catch (IOException e) {
                throw new ErroNaConversaoDaImagemEmByteArrayException("Erro na conversão da imagem para byte array!");
            }
        } catch (FileNotFoundException e) {
            throw new ArquivoNaoEncontradoException("Arquivo de imagem não encontrado!");
        }
        return imagem;
    }
}
