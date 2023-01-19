package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosEditarUsuario;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class EditaUsuario {

	private final UsuarioRepository repository;
	
	private final SystemLogger logger;

	public EditaUsuario(UsuarioRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	@Transactional
	public DadosDeUsuario editar(Long id, DadosEditarUsuario dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Usuario> usuarioPeloCpf = repository.buscarPeloCpf(dados.cpf());
		if (usuarioPeloCpf.isPresent() && usuarioPeloCpf.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Usuário com cpf %s já cadastrado!", dados.cpf()));
		}
		Optional<Usuario> usuarioPeloRegistro = repository.buscarPeloRegistro(dados.registro());
		if (usuarioPeloRegistro.isPresent() && usuarioPeloRegistro.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Usuário com registro %s já cadastrado!", dados.registro()));
		}
		Optional<Usuario> usuario = repository.buscarPeloId(id);
		if(usuario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Usuário com id %d não encontrado!", id));
		}
		usuario.get().setNome(dados.nome());
		usuario.get().setCpf(dados.cpf());
		usuario.get().setRegistro(dados.registro());
		usuario.get().setEmail(new Email(dados.email()));
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		usuario.get().setPerfis(perfis);
		Usuario usuarioEditado = repository.editar(usuario.get());
		logger.info(
				String.format("Usuario %s - Usuario com id %d editado!", usuarioAutenticado.getRegistro(), usuarioEditado.getId())
		);
		return new DadosDeUsuario(usuarioEditado);
	}
}
