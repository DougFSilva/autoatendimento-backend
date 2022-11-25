package br.com.totemAutoatendimento.dominio.exception;

public class ViolacaoDeIntegridadeDeDadosException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ViolacaoDeIntegridadeDeDadosException(String mensagem) {
		super(mensagem);
	}
	
	public ViolacaoDeIntegridadeDeDadosException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
