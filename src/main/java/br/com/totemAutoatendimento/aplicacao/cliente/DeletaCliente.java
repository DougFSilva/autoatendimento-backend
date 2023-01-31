package br.com.totemAutoatendimento.aplicacao.cliente;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaCliente {

	private final ClienteRepository repository;

	private final StandardLogger logger;

	public DeletaCliente(ClienteRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public void deletar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Cliente> cliente = repository.buscarPeloId(id);
		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cliente com id %d não encontrado!", id));
		}
		repository.deletar(cliente.get());
		logger.info(String.format("Cliente com cpf %s deletado!", cliente.get().getCpf()), usuarioAutenticado);
	}
}
