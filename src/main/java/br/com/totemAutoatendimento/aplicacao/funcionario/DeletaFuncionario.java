package br.com.totemAutoatendimento.aplicacao.funcionario;

import java.util.Optional;

import javax.transaction.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import br.com.totemAutoatendimento.dominio.funcionario.FuncionarioRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaFuncionario {

	private final FuncionarioRepository repository;

	private final StandardLogger logger;

	public DeletaFuncionario(FuncionarioRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Funcionario> funcionario = repository.buscarPeloId(id);
		if (funcionario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Funcionário com id %d não encontrado!", id));
		}
		repository.deletar(funcionario.get());
		logger.info(String.format("Funcionário com matrícula %s deletado!", funcionario.get().getMatricula()), usuarioAutenticado);
	}

}
