package br.com.totemAutoatendimento.aplicacao.totem;

import java.util.Optional;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.totem.dto.DadosDeTotem;
import br.com.totemAutoatendimento.aplicacao.totem.dto.DadosEditarTotem;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.totem.TotemRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class EditaTotem {

	private final TotemRepository repository;

	private final UsuarioRepository usuarioRepository;

	private final StandardLogger logger;

	public EditaTotem(TotemRepository repository, UsuarioRepository usuarioRepository, StandardLogger logger) {
		this.repository = repository;
		this.usuarioRepository = usuarioRepository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeTotem editar(Long id, DadosEditarTotem dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Totem> totemPeloIdentificador = repository.buscarPeloIdentificador(dados.identificador());
		if (totemPeloIdentificador.isPresent() && totemPeloIdentificador.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Totem com identificador %s já cadastrado!", dados.identificador()));
		}
		Optional<Totem> totem = repository.buscarPeloId(id);
		if (totem.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Totem com id %d não encotrado!", id));
		}
		Optional<Usuario> usuarioPeloUsername = usuarioRepository.buscarPeloUsername(dados.username());
		if (usuarioPeloUsername.isPresent() && usuarioPeloUsername.get().getId() != totem.get().getUsuario().getId()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Usuário com username %s já cadastrado!", dados.username()));
		}
		totem.get().setIdentificador(dados.identificador());
		totem.get().setLocalDeInstalacao(dados.localDeInstalacao());
		totem.get().getUsuario().setUsername(dados.username());
		Totem totemEditado = repository.salvar(totem.get());
		logger.info(String.format("Totem com identificador %s editado!", totemEditado.getIdentificador()),
				usuarioAutenticado);
		return new DadosDeTotem(totemEditado);
	}

}
