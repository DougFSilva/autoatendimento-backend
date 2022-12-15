package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

public class CriarCliente {

	private ClienteRepository repository;

	public CriarCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	@Transactional
	public Cliente executar(DadosCriarCliente dados) {
		if(repository.buscarClientePorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Cliente com cpf " + dados.cpf() + " já cadastrado!");
		}
		Endereco endereco = new Endereco(null, dados.estado(), dados.cidade(), dados.bairro(), dados.rua(), dados.numero());
		Email email = new Email(dados.email());
		Cliente cliente = new Cliente(dados.nome(), dados.cpf(), dados.telefone(), email, endereco);
		return repository.criar(cliente);
	}
}
