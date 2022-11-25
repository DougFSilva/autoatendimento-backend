package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class VerificadorDeDadosDeUsuario {

	private UsuarioRepository repository;

	public VerificadorDeDadosDeUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public void executar(String cpf, String registro, Email email) {
		if(repository.buscarPorCpf(cpf).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Cpf " + cpf + " já existe na base de dados!");
		}
		if(repository.buscarPorRegistro(registro).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Registro " + registro + " já existe na base de dados!");
		}
		if(repository.buscarPorEmail(email).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Email " + email.getEndereco() + " já existe na base de dados!");
		}
	}

}
