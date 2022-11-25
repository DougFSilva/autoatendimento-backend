package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import java.util.List;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;

public class EditarUsuario {

	private UsuarioRepository repository;

	public EditarUsuario(UsuarioRepository repository) {
		this.repository = repository;
	}
	
	public DadosDeUsuario executar(DadosEditarUsuario dados) {
		if (repository.buscarPorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Usuário com cpf " + dados.cpf() + " já cadastrado!");
		}
		if (repository.buscarPorRegistro(dados.registro()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Usuário com registro " + dados.registro() + " já cadastrado!");
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
