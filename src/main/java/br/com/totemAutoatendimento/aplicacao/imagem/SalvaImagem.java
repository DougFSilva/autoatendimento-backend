package br.com.totemAutoatendimento.aplicacao.imagem;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class SalvaImagem {

	public void salvar(MultipartFile file, Class<?> tipoDeObjeto, String pathPastaImagens,
			Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		validarExtensaoDaImagem(file);
		String pastaImagem = pathPastaImagens + File.separator + tipoDeObjeto.getSimpleName().toLowerCase();
		if (!new File(pastaImagem).exists()) {
			new File(pastaImagem).mkdirs();
		}
		try {
			Files.copy(file.getInputStream(), Path.of(pastaImagem + File.separator + file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new ErroNoUploadDeArquivoException("Erro no processo de upload do arquivo!", e.getCause());
		}
	}

	private void validarExtensaoDaImagem(MultipartFile file) {
		String extensao = file.getContentType();
		if (extensao == null || (!extensao.equals("image/jpeg") && !extensao.equals("image/png"))) {
			throw new ViolacaoDeIntegridadeDeDadosException("O arquivo de imagem deve ser PNG ou JPG!");
		}
	}

}
