package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.List;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosEditarUsuario;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

@PreAuthorize("hasRole('ADMIN')")
public class EditaUsuario {

	private UsuarioRepository repository;

	public EditaUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public DadosDeUsuario editar(DadosEditarUsuario dados) {
		if (repository.buscarPeloCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Usuário com cpf " + dados.cpf() + " já cadastrado!");
		}
		if (repository.buscarPeloRegistro(dados.registro()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Usuário com registro " + dados.registro() + " já cadastrado!");
		}
		BuscaUsuarioPeloId buscaUsuarioPeloId = new BuscaUsuarioPeloId(repository);
		Usuario usuario = buscaUsuarioPeloId.buscar(dados.id());
		usuario.setNome(dados.nome());
		usuario.setCpf(dados.cpf());
		usuario.setRegistro(dados.registro());
		usuario.setEmail(new Email(dados.email()));
		List<Perfil> perfis = dados.tipoPerfil().stream().map(Perfil::new).toList();
		usuario.setPerfis(perfis);
		return new DadosDeUsuario(repository.editar(usuario));
	}
}
