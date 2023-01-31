package br.com.totemAutoatendimento.aplicacao.cliente;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveCliente {

	private final ClienteRepository repository;

	private final SystemLogger logger;

	public RemoveCliente(ClienteRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	@Transactional
	public void remover(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Cliente> cliente = repository.buscarPeloId(id);
		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cliente com id %d não encontrado!", id));
		}
		repository.remover(cliente.get());
		logger.info(
				String.format("Usuário %s - Cliente com cpf %s removido!", usuarioAutenticado.getRegistro(), cliente.get().getCpf())
		);
	}
}
