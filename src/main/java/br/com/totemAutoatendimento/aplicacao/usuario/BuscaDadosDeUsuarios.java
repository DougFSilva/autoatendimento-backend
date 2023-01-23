package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosDeUsuario;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class BuscaDadosDeUsuarios {

	private final UsuarioRepository repository;

	public BuscaDadosDeUsuarios(UsuarioRepository repository) {
		this.repository = repository;
	}

	public DadosDeUsuario buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Usuario> usuario = repository.buscarPeloId(id);
		if(usuario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Usuário com id %d não encontrado!", id));
		}
		return new DadosDeUsuario(usuario.get());
	}

	public DadosDeUsuario buscarPeloRegistro(String registro, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Usuario> usuario = repository.buscarPeloRegistro(registro);
		if(usuario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Usuário com registro %s não encontrado!", registro));
		}
		return new DadosDeUsuario(usuario.get());
	}

	public List<DadosDeUsuario> buscaTodos(Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		return repository.buscarTodos().stream().map(DadosDeUsuario::new).toList();
	}

}
