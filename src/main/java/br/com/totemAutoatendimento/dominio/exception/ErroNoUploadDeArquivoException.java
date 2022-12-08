package br.com.totemAutoatendimento.dominio.exception;

public class ErroNoUploadDeArquivoException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

	public ErroNoUploadDeArquivoException(String mensagem) {
		super(mensagem);
	}
	
	public ErroNoUploadDeArquivoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
