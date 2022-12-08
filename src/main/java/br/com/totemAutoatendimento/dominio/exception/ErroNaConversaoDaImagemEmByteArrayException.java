package br.com.totemAutoatendimento.dominio.exception;

public class ErroNaConversaoDaImagemEmByteArrayException extends RuntimeException{
    
    private static final long serialVersionUID = 1L;

    public ErroNaConversaoDaImagemEmByteArrayException(String mensagem) {
		super(mensagem);
	}
	
	public ErroNaConversaoDaImagemEmByteArrayException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
