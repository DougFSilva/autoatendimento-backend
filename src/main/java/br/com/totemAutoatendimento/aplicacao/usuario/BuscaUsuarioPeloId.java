package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

@PreAuthorize("hasRole('ADMIN')")
public class BuscaUsuarioPeloId {

	private UsuarioRepository repository;

	public BuscaUsuarioPeloId(UsuarioRepository repository) {
		this.repository = repository;
	}

	public Usuario buscar(Long id) {
		return repository.buscarPeloId(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException("Usuário com id " + id + " não encontrado!"));
	}

}
