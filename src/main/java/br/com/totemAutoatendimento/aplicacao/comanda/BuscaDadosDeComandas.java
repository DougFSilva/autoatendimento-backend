package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDeComandas {

	private final ComandaRepository repository;

	private final ClienteRepository clienteRepository;
	
	public BuscaDadosDeComandas(ComandaRepository repository, ClienteRepository clienteRepository) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
	}

	public DadosDeComanda buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Comanda> comanda = repository.buscarPeloId(id);
    	if(comanda.isEmpty()) {
    		throw new ObjetoNaoEncontradoException(String.format("Comanda com id %d não encontrada!", id));
    	}
		return new DadosDeComanda(comanda.get());
	}

	public DadosDeComanda buscarAbertaPeloCartao(String codigoCartao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Comanda> comanda = repository.buscarPeloCartao(codigoCartao, true);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Comanda com cartão %s não encontrada!", codigoCartao));
		}
		return new DadosDeComanda(comanda.get());
	}

	public Page<DadosDeComanda> buscarPeloCliente(Pageable paginacao, Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Cliente> cliente = clienteRepository.buscarPeloId(id);
		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cliente com id %d não encontrado!", id));
		}
		return repository.buscarPeloCliente(paginacao, cliente.get()).map(DadosDeComanda::new);
	}

	public Page<DadosDeComanda> buscarPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarPelaData(paginacao, LocalDateTime.of(dataInicial, LocalTime.MIN),
				LocalDateTime.of(dataFinal, LocalTime.MAX)).map(DadosDeComanda::new);
	}

	public Page<DadosDeComanda> buscarPeloTipoDePagamento(Pageable paginacao, TipoPagamento tipoPagamento, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarPeloTipoDePagamento(paginacao, tipoPagamento).map(DadosDeComanda::new);
	}

	public Page<DadosDeComanda> buscarAbertas(Pageable paginacao, Boolean aberta, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarAbertas(paginacao, aberta).map(DadosDeComanda::new);
	}

	public Page<DadosDeComanda> buscarTodas(Pageable paginacao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarTodas(paginacao).map(DadosDeComanda::new);
	}
}
