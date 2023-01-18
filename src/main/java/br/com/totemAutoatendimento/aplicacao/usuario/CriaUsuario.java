package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosCriarUsuario;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class CriaUsuario {

	private final UsuarioRepository repository;

	private final CodificadorDeSenha codificador;
	
	private final SystemLogger logger;

	public CriaUsuario(UsuarioRepository repository, CodificadorDeSenha codificador, final SystemLogger logger) {
		this.repository = repository;
		this.codificador = codificador;
		this.logger = logger;
	}
	
	@Transactional
	public Usuario criar(DadosCriarUsuario dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		if (repository.buscarPeloCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Usuário com cpf %s já cadastrado!", dados.cpf()));
		}
		if (repository.buscarPeloRegistro(dados.registro()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Usuário com registro %s já cadastrado!", dados.registro()));
		}
		Email email = new Email(dados.email());
		Password password = new Password(dados.senha(), this.codificador);
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		Usuario usuario = new Usuario(null, dados.nome(), dados.cpf(), dados.registro(), email, password, perfis);
		Usuario usuarioCriado = repository.criar(usuario);
		logger.info(
				String.format("Usuário %s - Usuário com registro %s criado!", 
						usuarioAutenticado.getRegistro(), usuarioCriado.getRegistro())
		);
		return usuarioCriado;
	}
}
