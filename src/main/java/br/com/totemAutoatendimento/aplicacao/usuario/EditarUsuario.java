package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class EditarUsuario {

	private UsuarioRepository repository;

	public EditarUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@Transactional
	public DadosDeUsuario executar(DadosEditarUsuario dados) {
		if (repository.buscarPorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Usuário com cpf " + dados.cpf() + " já cadastrado!");
		}
		if (repository.buscarPorRegistro(dados.registro()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Usuário com registro " + dados.registro() + " já cadastrado!");
		}
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		Usuario usuario = buscarUsuario.executar(dados.id());
		usuario.setNome(dados.nome());
		usuario.setCpf(dados.cpf());
		usuario.setRegistro(dados.registro());
		usuario.setEmail(new Email(dados.email()));
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		usuario.setPerfis(perfis);
		return new DadosDeUsuario(repository.editar(usuario));
	}
}
