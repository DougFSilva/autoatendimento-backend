package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class FechaComanda {

	private final ComandaRepository repository;

	private final PedidoRepository pedidoRepository;

	private final SystemLogger logger;

	public FechaComanda(ComandaRepository repository, PedidoRepository pedidoRepository, SystemLogger logger) {
		this.repository = repository;
		this.pedidoRepository = pedidoRepository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeComanda fechar(Long id, TipoPagamento tipoPagamento, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
		Comanda comanda = buscaComandaPeloId.buscar(id);
		if (!comanda.getAberta()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Comanda com id %d já está fechada!", comanda.getId()));
		}
		List<Pedido> pedidos = pedidoRepository.buscarPelaComanda(comanda);
		pedidos.forEach(pedido -> {
			if (!pedido.getEntregue()) {
				throw new RegrasDeNegocioException("Não é possível fechar comanda pois há pedidos não entregues!");
			}
		});
		comanda.setAberta(false);
		comanda.setTipoPagamento(tipoPagamento);
		comanda.setFechamento(LocalDateTime.now());
		Comanda comandaFechada = repository.editar(comanda);
		logger.info(
				String.format("Usuário %s - Comanda com id %d fechada!", usuarioAutenticado.getRegistro(), comandaFechada.getId())
		);
		return new DadosDeComanda(comandaFechada);
	}
}
