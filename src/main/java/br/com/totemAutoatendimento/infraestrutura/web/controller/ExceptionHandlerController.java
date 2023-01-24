package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import br.com.totemAutoatendimento.dominio.exception.ArquivoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ErroNaAutenticacaoDeUsuario;
import br.com.totemAutoatendimento.dominio.exception.ErroNaConversaoDaImagemEmByteArrayException;
import br.com.totemAutoatendimento.dominio.exception.ErroNoUploadDeArquivoException;
import br.com.totemAutoatendimento.dominio.exception.ExceptionPadrao;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.exception.UsuarioSemPermissaoException;
import br.com.totemAutoatendimento.dominio.exception.VerificacaoDeSenhaException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

@ControllerAdvice
public class ExceptionHandlerController {

	@Value("${spring.servlet.multipart.max-request-size}")
	private String TamanhoMaximoDeImagem;

	@ExceptionHandler(ObjetoNaoEncontradoException.class)
	public ResponseEntity<ExceptionPadrao> objetoNaoEncontradoException(ObjetoNaoEncontradoException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.NOT_FOUND.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionPadrao);
	}
	
	@ExceptionHandler(ViolacaoDeIntegridadeDeDadosException.class)
	public ResponseEntity<ExceptionPadrao> violacaoDeIntegridadeDeDadosException(ViolacaoDeIntegridadeDeDadosException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ExceptionPadrao> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
		List<String> campos = exception.getFieldErrors().stream().map(campo -> campo.getField()).toList();
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "Erro nos campos " + campos);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}
	
	@ExceptionHandler(VerificacaoDeSenhaException.class)
	public ResponseEntity<ExceptionPadrao> verificacaoDeSenhaException(VerificacaoDeSenhaException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}

	@ExceptionHandler(ErroNoUploadDeArquivoException.class)
	public ResponseEntity<ExceptionPadrao> erroNoUploadDeArquivoException(ErroNoUploadDeArquivoException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}

	@ExceptionHandler(ArquivoNaoEncontradoException.class)
	public ResponseEntity<ExceptionPadrao> arquivoNaoEncontradoException(ArquivoNaoEncontradoException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionPadrao);
	}

	@ExceptionHandler(ErroNaConversaoDaImagemEmByteArrayException.class)
	public ResponseEntity<ExceptionPadrao> erroNaConversaoDaImagemEmByteArrayException(ErroNaConversaoDaImagemEmByteArrayException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ExceptionPadrao> maxUploadSizeExceededException(MaxUploadSizeExceededException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), "O tamanho da imagem deve ser no máximo " + TamanhoMaximoDeImagem);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}

	@ExceptionHandler(RegrasDeNegocioException.class)
	public ResponseEntity<ExceptionPadrao> regrasDeNegocioException(RegrasDeNegocioException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}

	@ExceptionHandler(ErroNaAutenticacaoDeUsuario.class)
	public ResponseEntity<ExceptionPadrao> erroNaAutenticacaoDeUsuario(ErroNaAutenticacaoDeUsuario exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}

	@ExceptionHandler(UsuarioSemPermissaoException.class)
	public ResponseEntity<ExceptionPadrao> usuarioSemPermissaoException(UsuarioSemPermissaoException exception) {
		ExceptionPadrao exceptionPadrao = new ExceptionPadrao(System.currentTimeMillis(),
				HttpStatus.BAD_REQUEST.value(), exception.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionPadrao);
	}


	

	
}
