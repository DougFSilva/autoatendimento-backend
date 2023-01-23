package br.com.totemAutoatendimento.aplicacao.comanda;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
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
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Comanda> comanda = repository.buscarPeloId(id);
    	if(comanda.isEmpty()) {
    		throw new ObjetoNaoEncontradoException(String.format("Comanda com id %d não encontrada!", id));
    	}
		if (!comanda.get().getAberta()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Comanda com id %d já está fechada!", comanda.get().getId()));
		}
		List<Pedido> pedidos = pedidoRepository.buscarPelaComanda(comanda.get());
		pedidos.forEach(pedido -> {
			if (!pedido.getEntregue()) {
				throw new RegrasDeNegocioException("Não é possível fechar comanda pois há pedidos não entregues!");
			}
		});
		comanda.get().setAberta(false);
		comanda.get().setTipoPagamento(tipoPagamento);
		comanda.get().setFechamento(LocalDateTime.now());
		Comanda comandaFechada = repository.editar(comanda.get());
		logger.info(
				String.format("Usuário %s - Comanda com id %d fechada!", usuarioAutenticado.getRegistro(), comandaFechada.getId())
		);
		return new DadosDeComanda(comandaFechada);
	}
}
