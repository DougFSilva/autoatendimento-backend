package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.cliente.BuscaClientePeloId;
import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscaDadosDeComandas {

	private final ComandaRepository repository;

	private final ClienteRepository clienteRepository;

	public BuscaDadosDeComandas(ComandaRepository repository, ClienteRepository clienteRepository) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public DadosDeComanda buscarPeloId(Long id) {
		BuscaComandaPeloId buscarComandaPeloId = new BuscaComandaPeloId(repository);
		Comanda comanda = buscarComandaPeloId.buscar(id);
		return new DadosDeComanda(comanda);
	}

	public DadosDeComanda buscarAbertasPeloCartao(String cartao) {
		Optional<Comanda> comanda = repository.buscarPeloCartao(cartao, true);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Comanda com carta :" + cartao + " não encontrada!");
		}
		return new DadosDeComanda(comanda.get());
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Page<DadosDeComanda> buscarPeloCliente(Pageable paginacao, Long id) {
		BuscaClientePeloId buscarClientePeloId = new BuscaClientePeloId(clienteRepository);
		Cliente cliente = buscarClientePeloId.buscar(id);
		return repository.buscarPeloCliente(paginacao, cliente).map(DadosDeComanda::new);
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Page<DadosDeComanda> buscarPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal) {
		return repository.buscarPelaData(paginacao, LocalDateTime.of(dataInicial, LocalTime.MIN),
				LocalDateTime.of(dataFinal, LocalTime.MAX)).map(DadosDeComanda::new);
	}

	@PreAuthorize("hasAnyRole('OPERATOR','ADMIN')")
	public Page<DadosDeComanda> buscarPeloTipoDePagamento(Pageable paginacao, TipoPagamento tipoPagamento) {
		return repository.buscarPeloTipoDePagamento(paginacao, tipoPagamento).map(DadosDeComanda::new);
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Page<DadosDeComanda> buscarAbertas(Pageable paginacao, Boolean aberta) {
		return repository.buscarAbertas(paginacao, aberta).map(DadosDeComanda::new);
	}

	@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
	public Page<DadosDeComanda> buscarTodas(Pageable paginacao) {
		return repository.buscarTodas(paginacao).map(DadosDeComanda::new);
	}
}
