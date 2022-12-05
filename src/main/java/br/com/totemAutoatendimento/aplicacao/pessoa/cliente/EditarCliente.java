package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.Endereco;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class EditarCliente {

	private ClienteRepository repository;

	public EditarCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public DadosDeCliente executar(DadosEditarCliente dados) {
		Optional<Cliente> clientePorCpf = repository.buscarClientePorCpf(dados.cpf());
		if(clientePorCpf.isPresent() && clientePorCpf.get().getId() != dados.id()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Cliente com cpf " + dados.cpf() + " já cadastrado!");
		}
		BuscarCliente buscarCliente = new BuscarCliente(repository);
		Cliente cliente = buscarCliente.executar(dados.id());
		cliente.setNome(dados.nome());
		cliente.setCpf(dados.cpf());
		cliente.setTelefone(dados.telefone());
		cliente.setEmail(new Email(dados.email()));
		Endereco endereco = new Endereco(null, dados.estado(), dados.cidade(), dados.bairro(), dados.rua(), dados.numero());
		cliente.setEndereco(endereco);
		return new DadosDeCliente(repository.editar(cliente));
		
	}
	
}
