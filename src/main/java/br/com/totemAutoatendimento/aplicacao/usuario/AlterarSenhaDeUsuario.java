package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.totemAutoatendimento.dominio.exception.VerificacaoDeSenhaException;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class AlterarSenhaDeUsuario {

	private UsuarioRepository repository;
	
	private CodificadorDeSenha codificador;

	public AlterarSenhaDeUsuario(UsuarioRepository repository, CodificadorDeSenha codificador) {
		this.repository = repository;
		this.codificador = codificador;
	}
	
	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public void executar(DadosAlterarSenhaDeUsuario dados) {
		Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		BuscarUsuario buscarUsuario = new BuscarUsuario(repository);
		Usuario usuario = buscarUsuario.executar(usuarioAutenticado.getId());
		if(!codificador.comparar(dados.senhaAtual(), usuario.getPassword())) {
			throw new VerificacaoDeSenhaException("Senha atual não confere!");
		}
		Password password = new Password(dados.novaSenha(), codificador);
		usuario.setPassword(password);
		repository.editar(usuario);
	}
}
