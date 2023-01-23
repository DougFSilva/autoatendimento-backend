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
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
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
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Cliente> clientePorCpf = repository.buscarClientePorCpf(dados.cpf());
		if (clientePorCpf.isPresent() && clientePorCpf.get().getId() != id) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Cliente com cpf %s já cadastrado!", dados.cpf()));
		}
		Optional<Cliente> cliente = repository.buscarPeloId(id);
		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cliente com id %d não encontrado!", id));
		}
		cliente.get().setNome(dados.nome());
		cliente.get().setCpf(dados.cpf());
		cliente.get().setTelefone(dados.telefone());
		cliente.get().setEmail(new Email(dados.email()));
		cliente.get().getEndereco().setEstado(dados.estado());
		cliente.get().getEndereco().setCidade(dados.cidade());
		cliente.get().getEndereco().setBairro(dados.bairro());
		cliente.get().getEndereco().setRua(dados.numero());
		cliente.get().getEndereco().setNumero(dados.numero());
		Cliente clienteEditado = repository.editar(cliente.get());
		logger.info(
				String.format("Usuário %s - Cliente com cpf %s editado!", usuarioAutenticado.getRegistro(),clienteEditado.getCpf())
		);
		return new DadosDeCliente(clienteEditado);
	}

}
