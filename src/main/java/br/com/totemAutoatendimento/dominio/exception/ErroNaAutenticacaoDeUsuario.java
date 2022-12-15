package br.com.totemAutoatendimento.dominio.exception;

public class ErroNaAutenticacaoDeUsuario extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public ErroNaAutenticacaoDeUsuario(String mensagem) {
		super(mensagem);
	}
	
	public ErroNaAutenticacaoDeUsuario(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
