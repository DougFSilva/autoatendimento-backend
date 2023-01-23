package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosAlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.VerificacaoDeSenhaException;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class AlteraSenhaDeUsuario {

	private final UsuarioRepository repository;
	
	private final CodificadorDeSenha codificador;
	
	public AlteraSenhaDeUsuario(UsuarioRepository repository, CodificadorDeSenha codificador) {
		this.repository = repository;
		this.codificador = codificador;
	}
	
	public void alterar(Long id, DadosAlterarSenhaDeUsuario dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Usuario> usuario = repository.buscarPeloId(id);
		if(usuario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Usuário com id %d não encontrado!", id));
		}
		if(!codificador.comparar(dados.senhaAtual(), usuario.get().getPassword())) {
			throw new VerificacaoDeSenhaException("Senha atual não confere!");
		}
		Password password = new Password(dados.novaSenha(), codificador);
		usuario.get().setPassword(password);
		repository.editar(usuario.get());
	}
}
