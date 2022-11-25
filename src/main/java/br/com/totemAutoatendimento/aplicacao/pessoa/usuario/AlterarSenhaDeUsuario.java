package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import br.com.totemAutoatendimento.dominio.exception.VerificacaoDeSenhaException;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Password;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class AlterarSenhaDeUsuario {

	private UsuarioRepository repository;
	
	private CodificadorDeSenha codificador;

	public AlterarSenhaDeUsuario(UsuarioRepository repository, CodificadorDeSenha codificador) {
		this.repository = repository;
		this.codificador = codificador;
	}
	
	public void executar(DadosAlterarSenhaDeUsuario dados) {
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		Usuario usuario = buscarUsuario.executar(dados.id());
		if(!codificador.comparar(dados.senhaAtual(), usuario.getPassword().getSenha())) {
			throw new VerificacaoDeSenhaException("Senha atual não confere!");
		}
		Password password = new Password(dados.novaSenha(), codificador);
		usuario.setPassword(password);
		repository.editar(usuario);
	}
}
