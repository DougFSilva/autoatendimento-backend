package br.com.totemAutoatendimento.aplicacao.cliente;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosEditarCliente;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EditaCliente {

	private final ClienteRepository repository;

	private final SystemLogger logger;

	public EditaCliente(ClienteRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeCliente editar(Long id, DadosEditarCliente dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Cliente> clientePorCpf = repository.buscarClientePorCpf(dados.cpf());
		if (clientePorCpf.isPresent() && clientePorCpf.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Cliente com cpf %s já cadastrado!", dados.cpf()));
		}
		BuscaClientePeloId buscaClientePeloId = new BuscaClientePeloId(repository);
		Cliente cliente = buscaClientePeloId.buscar(id);
		cliente.setNome(dados.nome());
		cliente.setCpf(dados.cpf());
		cliente.setTelefone(dados.telefone());
		cliente.setEmail(new Email(dados.email()));
		cliente.getEndereco().setEstado(dados.estado());
		cliente.getEndereco().setCidade(dados.cidade());
		cliente.getEndereco().setBairro(dados.bairro());
		cliente.getEndereco().setRua(dados.numero());
		cliente.getEndereco().setNumero(dados.numero());
		Cliente clienteEditado = repository.editar(cliente);
		logger.info(
				String.format("Usuário %s - Cliente com cpf %s editado!", usuarioAutenticado.getRegistro(),clienteEditado.getCpf())
		);
		return new DadosDeCliente(clienteEditado);
	}

}
