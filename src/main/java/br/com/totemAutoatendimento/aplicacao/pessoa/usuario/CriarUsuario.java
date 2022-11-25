package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import java.util.List;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.DadosCriarUsuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Password;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class CriarUsuario {

	private UsuarioRepository repository;
	
	private CodificadorDeSenha codificador;
	
	public CriarUsuario(UsuarioRepository repository, CodificadorDeSenha codificador) {
		this.repository = repository;
		this.codificador = codificador;
	}
	
	public Usuario executar(DadosCriarUsuario dados) {
		Email email = new Email(dados.email());
		if(repository.buscarPorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Cpf " + dados.cpf() + " já existe na base de dados!");
		}
		if(repository.buscarPorRegistro(dados.registro()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Registro " + dados.registro() + " já existe na base de dados!");
		}
		if(repository.buscarPorEmail(email).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Email " + email.getEndereco() + " já existe na base de dados!");
		}
		Password password = new Password(dados.senha(), this.codificador);
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		Usuario usuario = new Usuario(null, dados.nome(), dados.cpf(),dados.registro(), email, password, perfis);
		return repository.criar(usuario);
	}
}
