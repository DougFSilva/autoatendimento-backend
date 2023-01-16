package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosCriarCliente;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class CriaCliente {

	private final ClienteRepository repository;

	public CriaCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public Cliente criar(DadosCriarCliente dados) {
		if(repository.buscarClientePorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Cliente com cpf " + dados.cpf() + " já cadastrado!");
		}
		Endereco endereco = new Endereco(null, dados.estado(), dados.cidade(), dados.bairro(), dados.rua(), dados.numero());
		Email email = new Email(dados.email());
		Cliente cliente = new Cliente(dados.nome(), dados.cpf(), dados.telefone(), email, endereco);
		return repository.criar(cliente);
	}
}
