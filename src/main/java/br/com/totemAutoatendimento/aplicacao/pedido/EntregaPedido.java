
package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalTime;
import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EntregaPedido {

	private final PedidoRepository repository;

	private final StandardLogger logger;

	public EntregaPedido(PedidoRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public void entregar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Pedido> pedido = repository.buscarPeloId(id);
		if (pedido.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Pedido com id %d não encontrado", id));
		}
		pedido.get().setEntregue(true);
		pedido.get().setTempoEntrega(LocalTime.now());
		Pedido pedidoEntregue = repository.salvar(pedido.get());
		logger.info(String.format("Pedido com id %d entregue!", pedidoEntregue.getId()), usuarioAutenticado);
	}

	public void cancelarEntrega(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Pedido> pedido = repository.buscarPeloId(id);
		if (pedido.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Pedido com id %d não encontrado", id));
		}
		pedido.get().setEntregue(false);
		pedido.get().setTempoEntrega(null);
		Pedido pedidoEditado = repository.salvar(pedido.get());
		logger.info(String.format("Cancelada entrega do pedido com id %d!", pedidoEditado.getId()), usuarioAutenticado);
	}
}
