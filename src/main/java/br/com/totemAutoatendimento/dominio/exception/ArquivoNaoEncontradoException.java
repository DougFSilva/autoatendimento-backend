package br.com.totemAutoatendimento.dominio.exception;

public class ArquivoNaoEncontradoException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public ArquivoNaoEncontradoException(String mensagem) {
		super(mensagem);
	}
	
	public ArquivoNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
