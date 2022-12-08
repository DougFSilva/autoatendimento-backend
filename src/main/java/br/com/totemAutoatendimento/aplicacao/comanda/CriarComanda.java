package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.BuscarCliente;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class CriarComanda {

	private ComandaRepository repository;

	private ClienteRepository clienteRepository;

	public CriarComanda(ComandaRepository repository) {
		this.repository = repository;
	}

	public Comanda executar(DadosCriarComanda dados) {
		if (repository.buscarComandaPorCartao(dados.cartao(), true).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Não é possivel criar comanda, pois já existe uma aberta para esse cartão!");
		}
		BuscarCliente buscarCliente = new BuscarCliente(clienteRepository);
		Cliente cliente = buscarCliente.executar(dados.idCliente());
		Comanda comanda = new Comanda(dados.cartao(), cliente);
		return repository.criar(comanda);
	}

}
