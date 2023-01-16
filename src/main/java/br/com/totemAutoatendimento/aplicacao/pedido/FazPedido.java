package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.mercadoria.VerificaDisponibilidadeDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosFazerPedido;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;
import br.com.totemAutoatendimento.dominio.pedido.EventoDePedido;
import br.com.totemAutoatendimento.dominio.pedido.MensagemDePedido;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.pedido.TipoDeMensagemDePedido;

public class FazPedido {

	private final PedidoRepository repository;

	private final ComandaRepository comandaRepository;

	private final MercadoriaRepository mercadoriaRepository;

	private final EventoDePedido eventoDePedido;

	public FazPedido(PedidoRepository repository, ComandaRepository comandaRepository,
			MercadoriaRepository mercadoriaRepository, EventoDePedido eventoDePedido) {
		this.repository = repository;
		this.comandaRepository = comandaRepository;
		this.mercadoriaRepository = mercadoriaRepository;
		this.eventoDePedido = eventoDePedido;
	}

	@Transactional
	public DadosDeComanda fazer(String cartao, List<DadosFazerPedido> dados) {
		Optional<Comanda> comanda = comandaRepository.buscarPeloCartao(cartao, true);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException("Comanda aberta com cartão " + cartao + " não encontrada!");
		}
		dados.forEach(dado -> {
			VerificaDisponibilidadeDeMercadoria verificaDisponibilidadeDeMercadoria = new VerificaDisponibilidadeDeMercadoria(
					mercadoriaRepository);
			Mercadoria mercadoria = verificaDisponibilidadeDeMercadoria.verificar(dado.codigoDaMercadoria());
			Pedido pedido = new Pedido(
					null, 
					comanda.get(), 
					mercadoria, 
					dado.mesa(), 
					dado.quantidade(), 
					LocalDate.now(),
					LocalTime.now(), 
					null, 
					false);
			Pedido pedidoCriado = repository.criar(pedido);
			comanda.get().adicionarValor(pedidoCriado);
			eventoDePedido.notificar(
					new MensagemDePedido(TipoDeMensagemDePedido.PEDIDO_EFETUADO, new DadosDePedido(pedidoCriado)));
		});
		return new DadosDeComanda(comandaRepository.editar(comanda.get()));
	}

}
