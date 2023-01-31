package br.com.totemAutoatendimento.aplicacao.pedido;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.pedido.EventoDePedido;
import br.com.totemAutoatendimento.dominio.pedido.MensagemDePedido;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.pedido.TipoDeMensagemDePedido;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaPedido {

	private final PedidoRepository repository;

	private final ComandaRepository comandaRepository;

	private final EventoDePedido eventoDePedido;
	
	private final StandardLogger logger;

	public DeletaPedido(PedidoRepository repository, ComandaRepository comandaRepository,
			EventoDePedido eventoDePedido, StandardLogger logger) {
		this.repository = repository;
		this.comandaRepository = comandaRepository;
		this.eventoDePedido = eventoDePedido;
		this.logger = logger;
	}

	@Transactional
	public DadosDeComanda deletar(Long id, String codigoCartao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Comanda> comanda = comandaRepository.buscarPeloCartao(codigoCartao, true);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Comanda aberta com cartão %s não encontrada!", codigoCartao));
		}
		Optional<Pedido> pedido = repository.buscarPeloId(id);
		if (pedido.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Pedido com id %d não encontrado!", id));
		}
		if (pedido.get().getEntregue()) {
			throw new RegrasDeNegocioException("Não é possível cancelar pedido que já foi entregue!");
		}
		repository.deletar(pedido.get());
		comanda.get().removerValor(pedido.get().getValor());
		Comanda comandaAtualizada = comandaRepository.salvar(comanda.get());
		eventoDePedido.notificar(
				new MensagemDePedido(TipoDeMensagemDePedido.PEDIDO_REMOVIDO, new DadosDePedido(pedido.get()))
		);
		logger.info(String.format("Pedido com id %d cancelado!", pedido.get().getId()), usuarioAutenticado);
		return new DadosDeComanda(comandaAtualizada);
	}
	
}
