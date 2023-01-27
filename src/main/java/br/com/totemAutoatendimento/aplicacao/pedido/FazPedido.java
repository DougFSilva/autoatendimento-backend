package br.com.totemAutoatendimento.aplicacao.pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.VerificaDisponibilidadeDeMercadoria;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosFazerPedido;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
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
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class FazPedido {

	private final PedidoRepository repository;

	private final ComandaRepository comandaRepository;

	private final MercadoriaRepository mercadoriaRepository;

	private final EventoDePedido eventoDePedido;

	private final SystemLogger logger;

	public FazPedido(PedidoRepository repository, ComandaRepository comandaRepository,
			MercadoriaRepository mercadoriaRepository, EventoDePedido eventoDePedido, SystemLogger logger) {
		this.repository = repository;
		this.comandaRepository = comandaRepository;
		this.mercadoriaRepository = mercadoriaRepository;
		this.eventoDePedido = eventoDePedido;
		this.logger = logger;
	}

	@Transactional
	public DadosDeComanda fazer(String codigoCartao, List<DadosFazerPedido> dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(null);
		Optional<Comanda> comanda = comandaRepository.buscarPeloCartao(codigoCartao, true);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException(
					String.format("Comanda aberta com cartão de código %s não encontrada!", codigoCartao));
		}
		dados.forEach(dado -> {
			VerificaDisponibilidadeDeMercadoria verificaDisponibilidadeDeMercadoria = new VerificaDisponibilidadeDeMercadoria(
					mercadoriaRepository);
			Mercadoria mercadoria = verificaDisponibilidadeDeMercadoria.verificar(dado.codigoDaMercadoria());
			Pedido pedido = new Pedido(comanda.get(), mercadoria, dado.mesa(), dado.quantidade());
			Pedido pedidoCriado = repository.criar(pedido);
			comanda.get().adicionarValor(pedidoCriado.getValor());
			eventoDePedido.notificar(
					new MensagemDePedido(TipoDeMensagemDePedido.PEDIDO_EFETUADO, new DadosDePedido(pedidoCriado))
			);
			logger.info(
					String.format("Pedido com id %d criado para comanda com id %d!", pedidoCriado.getId(), comanda.get().getId())
			);
		});
		return new DadosDeComanda(comandaRepository.editar(comanda.get()));
	}

}
