package br.com.totemAutoatendimento.infraestrutura.seguranca;

import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.pessoa.usuario.CodificadorDeSenha;

@Service
public class CodificadorBcrypt implements CodificadorDeSenha{

	@Override
	public String codificar(String senha) {
		return senha + "_codificada";
	}

	@Override
	public boolean comparar(String senha, String senhaCodificada) {
		return (senha + "_codificada").equals(senhaCodificada);
	}

	
}
