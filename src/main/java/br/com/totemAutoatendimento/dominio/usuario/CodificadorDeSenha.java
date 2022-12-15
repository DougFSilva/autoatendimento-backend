package br.com.totemAutoatendimento.dominio.usuario;

public interface CodificadorDeSenha {

	String codificar(String senha);
	
	boolean comparar(String senha, String senhaCodificada);
}
