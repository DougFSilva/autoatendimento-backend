package br.com.totemAutoatendimento.infraestrutura.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;

@Service
public class CodificadorBcrypt implements CodificadorDeSenha {

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Override
	public String codificar(String senha) {
		return encoder.encode(senha);
	}

	@Override
	public boolean comparar(String senha, String senhaCodificada) {
		return encoder.matches(senha, senhaCodificada);
	}

}
