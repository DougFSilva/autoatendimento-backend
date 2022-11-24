package br.com.totemAutoatendimento.dominio.exception;

public class EmailInvalidoException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public EmailInvalidoException(String mensagem) {
		super(mensagem);
	}
	
	public EmailInvalidoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
