package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.cliente.BuscaClientePeloId;
import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosCriarComanda;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class CriaComanda {

	private final ComandaRepository repository;

	private final ClienteRepository clienteRepository;

	public CriaComanda(ComandaRepository repository, ClienteRepository clienteRepository) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
	}

	@Transactional
	public Comanda criar(DadosCriarComanda dados) {
		if (repository.buscarPeloCartao(dados.cartao(), true).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					"Comanda aberta existente para esse cartão!");
		}
		BuscaClientePeloId buscaClientePeloId = new BuscaClientePeloId(clienteRepository);
		Cliente cliente = buscaClientePeloId.buscar(dados.idCliente());
		Comanda comanda = new Comanda(dados.cartao(), cliente);
		return repository.criar(comanda);
	}

}
