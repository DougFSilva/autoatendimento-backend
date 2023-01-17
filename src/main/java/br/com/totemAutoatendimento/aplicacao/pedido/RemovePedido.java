package br.com.totemAutoatendimento.aplicacao.pedido;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.pedido.EventoDePedido;
import br.com.totemAutoatendimento.dominio.pedido.MensagemDePedido;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.pedido.TipoDeMensagemDePedido;

public class RemovePedido {

	private final PedidoRepository repository;

	private final ComandaRepository comandaRepository;

	private final EventoDePedido eventoDePedido;

	public RemovePedido(PedidoRepository repository, ComandaRepository comandaRepository,
			EventoDePedido eventoDePedido) {
		this.repository = repository;
		this.comandaRepository = comandaRepository;
		this.eventoDePedido = eventoDePedido;
	}

	@Transactional
	public DadosDeComanda remover(Long id, String codigoCartao) {
		Optional<Comanda> comanda = comandaRepository.buscarPeloCartao(codigoCartao, true);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Comanda aberta com cartão " + codigoCartao + " não encontrada!");
		}
		Optional<Pedido> pedido = repository.buscarPeloId(id);
		if (pedido.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Pedido com id " + id + " não encontrado!");
		}
		if (pedido.get().getEntregue()) {
			throw new RegrasDeNegocioException("Não é possível cancelar pedido que já foi entregue!");
		}
		repository.remover(pedido.get());
		comanda.get().removerValor(pedido.get().getValor());
		Comanda comandaAtualizada = comandaRepository.editar(comanda.get());
		eventoDePedido.notificar(
				new MensagemDePedido(TipoDeMensagemDePedido.PEDIDO_REMOVIDO, new DadosDePedido(pedido.get())));
		return new DadosDeComanda(comandaAtualizada);
	}
	
}
