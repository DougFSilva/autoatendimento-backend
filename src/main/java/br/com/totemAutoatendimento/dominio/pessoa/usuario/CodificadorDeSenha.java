package br.com.totemAutoatendimento.dominio.pessoa.usuario;

public interface CodificadorDeSenha {

	String codificar(String senha);
	
	boolean comparar(String senha, String senhaCodificada);
}
