package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.cliente.BuscarCliente;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

public class CriarComanda {

	private ComandaRepository repository;

	private ClienteRepository clienteRepository;

	public CriarComanda(ComandaRepository repository, ClienteRepository clienteRepository) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	@Transactional
	public Comanda executar(DadosCriarComanda dados) {
		if (repository.buscarPorCartao(dados.cartao(), true).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Comanda aberta existente para esse cartão!");
		}
		BuscarCliente buscarCliente = new BuscarCliente(clienteRepository);
		Cliente cliente = buscarCliente.executar(dados.idCliente());
		Comanda comanda = new Comanda(dados.cartao(), cliente);
		return repository.criar(comanda);
	}

}
