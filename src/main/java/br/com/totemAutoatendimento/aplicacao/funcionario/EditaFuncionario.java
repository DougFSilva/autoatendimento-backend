package br.com.totemAutoatendimento.aplicacao.funcionario;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.funcionario.dto.DadosDeFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.dto.DadosEditarFuncionario;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import br.com.totemAutoatendimento.dominio.funcionario.FuncionarioRepository;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class EditaFuncionario {

	private final FuncionarioRepository repository;

	private final UsuarioRepository usuarioRepository;

	private final StandardLogger logger;

	public EditaFuncionario(FuncionarioRepository repository, UsuarioRepository usuarioRepository,
			StandardLogger logger) {
		this.repository = repository;
		this.usuarioRepository = usuarioRepository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeFuncionario editar(Long id, DadosEditarFuncionario dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Funcionario> funcionarioPeloCpf = repository.buscarPeloCpf(dados.cpf());
		if (funcionarioPeloCpf.isPresent() && funcionarioPeloCpf.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Funcionário com cpf %s já cadastrado!", dados.cpf()));
		}
		Optional<Funcionario> funcionarioPelaMatricula = repository.buscarPelaMatricula(dados.matricula());
		if (funcionarioPelaMatricula.isPresent() && funcionarioPelaMatricula.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Funcionário com matrícula %s já cadastrado!", dados.matricula()));
		}
		Optional<Funcionario> funcionario = repository.buscarPeloId(id);
		if (funcionario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Funcionário com id %d não encontrado!", id));
		}
		Optional<Usuario> usuarioPeloUsername = usuarioRepository.buscarPeloUsername(dados.matricula());
		if (usuarioPeloUsername.isPresent()
				&& usuarioPeloUsername.get().getId() != funcionario.get().getUsuario().getId()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Usuário com username %s já cadastrado!", dados.matricula()));
		}
		funcionario.get().setMatricula(dados.matricula());
		funcionario.get().setNome(dados.nome());
		funcionario.get().setCpf(dados.cpf());
		funcionario.get().setEmail(new Email(dados.email()));
		funcionario.get().getUsuario().setUsername(dados.matricula());
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		funcionario.get().getUsuario().setPerfis(perfis);
		Funcionario funcionarioEditado = repository.salvar(funcionario.get());
		logger.info(String.format("Funcionário com matricula %s editado!", funcionarioEditado.getMatricula()), usuarioAutenticado);
		return new DadosDeFuncionario(funcionarioEditado);
	}
}
