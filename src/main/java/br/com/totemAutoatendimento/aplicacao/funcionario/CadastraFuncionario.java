package br.com.totemAutoatendimento.aplicacao.funcionario;

import java.util.List;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.funcionario.dto.DadosCriarFuncionario;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import br.com.totemAutoatendimento.dominio.funcionario.FuncionarioRepository;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class CadastraFuncionario {

	private final FuncionarioRepository repository;

	private final UsuarioRepository usuarioRepository;

	private final CodificadorDeSenha codificadorDeSenha;

	private final StandardLogger logger;

	public CadastraFuncionario(FuncionarioRepository repository, UsuarioRepository usuarioRepository,
			CodificadorDeSenha codificadorDeSenha, StandardLogger logger) {
		this.repository = repository;
		this.usuarioRepository = usuarioRepository;
		this.codificadorDeSenha = codificadorDeSenha;
		this.logger = logger;
	}

	@Transactional
	public Funcionario cadastrar(DadosCriarFuncionario dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		if (repository.buscarPeloCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Funcionário com cpf %s já cadastrado!", dados.cpf()));
		}
		if (repository.buscarPelaMatricula(dados.matricula()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Funcionário com matricula %s já cadastrado!", dados.matricula()));
		}
		if(usuarioRepository.buscarPeloUsername(dados.matricula()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Usuário com username %s já cadastrado!", dados.matricula()));
		}
		Password password = new Password(dados.senha(), this.codificadorDeSenha);
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		Usuario usuario = new Usuario(null, dados.matricula(), password, perfis);
		Email email = new Email(dados.email());
		Funcionario funcionario = new Funcionario(null, dados.matricula(), dados.nome(), dados.cpf(), email, usuario);
		Funcionario funcionarioCriado = repository.salvar(funcionario);
		logger.info(String.format("Funcionário com matrícula %s cadastrado!", funcionarioCriado.getMatricula()), usuarioAutenticado);
		return funcionarioCriado;
	}
}
