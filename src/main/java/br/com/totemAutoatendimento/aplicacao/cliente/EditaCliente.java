package br.com.totemAutoatendimento.aplicacao.cliente;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosEditarCliente;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class EditaCliente {

	private final ClienteRepository repository;

	public EditaCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public DadosDeCliente editar(DadosEditarCliente dados) {
		Optional<Cliente> clientePorCpf = repository.buscarClientePorCpf(dados.cpf());
		if(clientePorCpf.isPresent() && clientePorCpf.get().getId() != dados.id()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Cliente com cpf " + dados.cpf() + " já cadastrado!");
		}
		BuscaClientePeloId buscaClientePeloId = new BuscaClientePeloId(repository);
		Cliente cliente = buscaClientePeloId.buscar(dados.id());
		cliente.setNome(dados.nome());
		cliente.setCpf(dados.cpf());
		cliente.setTelefone(dados.telefone());
		cliente.setEmail(new Email(dados.email()));
		Endereco endereco = new Endereco(null, dados.estado(), dados.cidade(), dados.bairro(), dados.rua(), dados.numero());
		cliente.setEndereco(endereco);
		return new DadosDeCliente(repository.editar(cliente));
	}
	
}
