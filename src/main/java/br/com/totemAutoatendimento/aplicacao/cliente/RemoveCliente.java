package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
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
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		BuscaClientePeloId buscaClientePeloId = new BuscaClientePeloId(repository);
		Cliente cliente = buscaClientePeloId.buscar(id);
		repository.remover(cliente);
		logger.info(
				String.format("Usuário %s - Cliente com cpf %s removido!", usuarioAutenticado.getRegistro(), cliente.getCpf())
		);
	}
}
