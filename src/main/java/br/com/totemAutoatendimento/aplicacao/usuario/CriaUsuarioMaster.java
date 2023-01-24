package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosCriarUsuario;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class CriaUsuarioMaster {

	private final UsuarioRepository repository;

	private final CodificadorDeSenha codificadorDeSenha;
	
	private final SystemLogger logger;

	public CriaUsuarioMaster(UsuarioRepository repository, CodificadorDeSenha codificadorDeSenha, final SystemLogger logger) {
		this.repository = repository;
		this.codificadorDeSenha = codificadorDeSenha;
		this.logger = logger;
	}
	
	public void criar(DadosCriarUsuario dados) {
		Email email = new Email(dados.email());
		Password password = new Password(dados.senha(), codificadorDeSenha);
		List<Perfil> perfis = dados.tipoPerfil().stream().map(tipoPerfil -> new Perfil(tipoPerfil)).toList();
		Usuario usuario = new Usuario(null, dados.nome(), dados.cpf(), dados.registro(), email, password, perfis);
		if(repository.buscarPeloRegistro(dados.registro()).isEmpty()) {
			repository.criar(usuario);
			logger.info("Sistema - Usuário Master Criado!");
		};
	}
	
	
}
