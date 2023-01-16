package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosDeUsuario;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

@PreAuthorize("hasRole('ADMIN')")
public class BuscaDadosDeUsuarios {

	private UsuarioRepository repository;

	public BuscaDadosDeUsuarios(UsuarioRepository repository) {
		this.repository = repository;
	}

	public DadosDeUsuario buscarPeloId(Long id) {
		BuscaUsuarioPeloId buscaUsuarioPeloId = new BuscaUsuarioPeloId(repository);
		return new DadosDeUsuario(buscaUsuarioPeloId.buscar(id));
	}

	public Usuario buscarPeloRegistro(String registro) {
		Optional<Usuario> usuario = repository.buscarPeloRegistro(registro);
		return usuario.orElseThrow(
				() -> new ObjetoNaoEncontradoException("Usuário com registro " + registro + " não encontrado!"));
	}

	public List<DadosDeUsuario> buscaTodos() {
		return repository.buscarTodos().stream().map(DadosDeUsuario::new).toList();
	}

}
