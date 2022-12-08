package br.com.totemAutoatendimento.dominio.exception;

public class RegrasDeNegocioException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public RegrasDeNegocioException(String mensagem) {
		super(mensagem);
	}
	
	public RegrasDeNegocioException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}