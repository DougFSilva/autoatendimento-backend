package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.Optional;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
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

	private final StandardLogger logger;

	public AlteraSenhaDeUsuario(UsuarioRepository repository, CodificadorDeSenha codificador,
			final StandardLogger logger) {
		this.repository = repository;
		this.codificador = codificador;
		this.logger = logger;
	}

	@Transactional
	public void alterar(DadosAlterarSenhaDeUsuario dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Usuario> usuario = repository.buscarPeloId(usuarioAutenticado.getId());
		if (usuario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(
					String.format("Usuário com id %d não encontrado!", usuarioAutenticado.getId()));
		}
		if (!codificador.comparar(dados.senhaAtual(), usuario.get().getPassword().getSenha())) {
			throw new VerificacaoDeSenhaException("Senha atual não confere!");
		}
		Password password = new Password(dados.novaSenha(), codificador);
		usuario.get().setPassword(password);
		repository.salvar(usuario.get());
		logger.info(String.format("Alterada senha de funcionário de id %d", usuario.get().getId()), usuarioAutenticado);
	}
}
