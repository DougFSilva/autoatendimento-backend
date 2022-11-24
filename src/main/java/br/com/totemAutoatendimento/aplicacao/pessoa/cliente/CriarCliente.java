package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.Endereco;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.DadosCriarCliente;

public class CriarCliente {

	private ClienteRepository repository;
	
	public CriarCliente(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public Cliente executar(DadosCriarCliente dados) {
		Endereco endereco = new Endereco(null, dados.estado(), dados.cidade(), dados.bairro(), dados.rua(), dados.numero());
		Email email = new Email(dados.enderecoEmail());
		Cliente cliente = new Cliente(dados.nome(), dados.cpf(), dados.telefone(), email, endereco);
		return repository.criar(cliente);
	}
}
