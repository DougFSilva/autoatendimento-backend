package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosAlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.dominio.exception.VerificacaoDeSenhaException;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class AlteraSenhaDeUsuario {

	private UsuarioRepository repository;
	
	private CodificadorDeSenha codificador;

	public AlteraSenhaDeUsuario(UsuarioRepository repository, CodificadorDeSenha codificador) {
		this.repository = repository;
		this.codificador = codificador;
	}
	
	public void alterar(Long id, DadosAlterarSenhaDeUsuario dados) {
		BuscaUsuarioPeloId buscaUsuarioPeloId = new BuscaUsuarioPeloId(repository);
		Usuario usuario = buscaUsuarioPeloId.buscar(id);
		if(!codificador.comparar(dados.senhaAtual(), usuario.getPassword())) {
			throw new VerificacaoDeSenhaException("Senha atual não confere!");
		}
		Password password = new Password(dados.novaSenha(), codificador);
		usuario.setPassword(password);
		repository.editar(usuario);
	}
}
