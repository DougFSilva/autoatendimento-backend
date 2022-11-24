package br.com.totemAutoatendimento.dominio.exception;

public class SenhaInvalidaException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SenhaInvalidaException(String mensagem) {
		super(mensagem);
	}
	
	public SenhaInvalidaException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
