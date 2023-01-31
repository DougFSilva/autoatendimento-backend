package br.com.totemAutoatendimento.aplicacao.totem;

import java.util.Optional;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosAlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.VerificacaoDeSenhaException;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.totem.TotemRepository;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class AlteraSenhaDeTotem {

	private final TotemRepository repository;

	private final CodificadorDeSenha codificador;

	private final StandardLogger logger;

	public AlteraSenhaDeTotem(TotemRepository repository, CodificadorDeSenha codificador, final StandardLogger logger) {
		this.repository = repository;
		this.codificador = codificador;
		this.logger = logger;
	}

	@Transactional
	public void alterar(Long id, DadosAlterarSenhaDeUsuario dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Totem> totem = repository.buscarPeloId(id);
		if (totem.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Totem com id %d não encontrado!", id));
		}
		if (!codificador.comparar(dados.senhaAtual(), totem.get().getUsuario().getPassword().getSenha())) {
			throw new VerificacaoDeSenhaException("Senha atual não confere!");
		}
		Password password = new Password(dados.novaSenha(), codificador);
		totem.get().getUsuario().setPassword(password);
		repository.salvar(totem.get());
		logger.info(String.format("Alterada senha de Totem com identificador %s", totem.get().getIdentificador()),
				usuarioAutenticado);
	}
}
