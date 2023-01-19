package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosCriarComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CriaComanda {

	private final ComandaRepository repository;

	private final ClienteRepository clienteRepository;

	private final CartaoRepository cartaoRepository;

	private final SystemLogger logger;

	public CriaComanda(ComandaRepository repository, ClienteRepository clienteRepository,
			CartaoRepository cartaoRepository, SystemLogger logger) {
		this.repository = repository;
		this.clienteRepository = clienteRepository;
		this.cartaoRepository = cartaoRepository;
		this.logger = logger;
	}

	@Transactional
	public Comanda criar(DadosCriarComanda dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		if (repository.buscarPeloCartao(dados.codigoCartao(), true).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Comanda aberta existente para esse cartão!");
		}
		Optional<Cliente> cliente = clienteRepository.buscarPeloId(dados.clienteId());
		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cliente com id %d não encontrado!", dados.clienteId()));
		}
		Optional<Cartao> cartao = cartaoRepository.buscarPeloCodigo(dados.codigoCartao());
		if(cartao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cartão com código %s não encontrado!", dados.codigoCartao()));
		}
		Comanda comanda = new Comanda(cartao.get(), cliente.get());
		Comanda comandaCriada = repository.criar(comanda);
		logger.info(
				String.format("Usuário %s - Comanda com id %d aberta!", usuarioAutenticado.getRegistro(), comandaCriada.getId())
		);
		return comandaCriada;
	}

}
