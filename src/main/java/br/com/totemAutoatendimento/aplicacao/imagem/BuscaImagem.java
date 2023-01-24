package br.com.totemAutoatendimento.aplicacao.imagem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import br.com.totemAutoatendimento.dominio.exception.ArquivoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ErroNaConversaoDaImagemEmByteArrayException;

public class BuscaImagem {

    public byte[] buscar(String pathPastaImagens, Class<?> tipoDeObjeto, String nomeDaImagem) {
        File file = new File(pathPastaImagens
        		+ File.separator
        		+ tipoDeObjeto.getSimpleName().toLowerCase()
        		+ File.separator
        		+ nomeDaImagem);
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
