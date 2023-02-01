package br.com.totemAutoatendimento.aplicacao.totem;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.totem.dto.DadosCriarTotem;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.totem.TotemRepository;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class CadastraTotem {

	private final TotemRepository repository;

	private final UsuarioRepository usuarioRepository;

	private final CodificadorDeSenha codificadorDeSenha;

	private final StandardLogger logger;

	public CadastraTotem(TotemRepository repository, UsuarioRepository usuarioRepository,
			CodificadorDeSenha codificadorDeSenha, StandardLogger logger) {
		this.repository = repository;
		this.usuarioRepository = usuarioRepository;
		this.codificadorDeSenha = codificadorDeSenha;
		this.logger = logger;
	}

	@Transactional
	public Totem cadastrar(DadosCriarTotem dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		if (repository.buscarPeloIdentificador(dados.identificador()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Totem com identificador %s já cadastrado!", dados.identificador()));
		}
		if (usuarioRepository.buscarPeloUsername(dados.username()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Usuário com username %s já cadastrado!", dados.username()));
		}
		Password password = new Password(dados.senha(), codificadorDeSenha);
		List<Perfil> perfis = Arrays.asList(new Perfil(TipoPerfil.TOTEM));
		Usuario usuario = new Usuario(null, dados.username(), password, perfis);
		Totem totem = new Totem(null, dados.identificador(), dados.localDeInstalacao(), usuario);
		Totem totemCadastrado = repository.salvar(totem);
		logger.info(String.format("Totem com identificador %s cadastrado!", totemCadastrado.getIdentificador()),
				usuarioAutenticado);
		return totemCadastrado;
	}

}
