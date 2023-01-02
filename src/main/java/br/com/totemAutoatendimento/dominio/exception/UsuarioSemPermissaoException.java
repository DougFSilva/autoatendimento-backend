package br.com.totemAutoatendimento.dominio.exception;

public class UsuarioSemPermissaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsuarioSemPermissaoException(String mensagem) {
		super(mensagem);
	}
	
	public UsuarioSemPermissaoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}