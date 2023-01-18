
package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalTime;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EntregaPedido {

	private final PedidoRepository repository;
	
	private final SystemLogger logger;

	public EntregaPedido(PedidoRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public void entregar(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		BuscaPedidoPeloId buscaPedidoPeloId = new BuscaPedidoPeloId(repository);
		Pedido pedido = buscaPedidoPeloId.buscar(id);
		pedido.setEntregue(true);
		pedido.setTempoEntrega(LocalTime.now());
		Pedido pedidoEntregue = repository.editar(pedido);
		logger.info(
				String.format("Usuário %s - Pedido com id %d entregue!", usuarioAutenticado.getRegistro(), pedidoEntregue.getId())
		);
	}
	
	public void cancelarEntrega(Long id,  Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		BuscaPedidoPeloId buscaPedidoPeloId = new BuscaPedidoPeloId(repository);
		Pedido pedido = buscaPedidoPeloId.buscar(id);
		pedido.setEntregue(false);
		pedido.setTempoEntrega(null);
		Pedido pedidoEditado = repository.editar(pedido);
		logger.info(
				String.format("Usuário %s - Cancelada entrega do pedido com id %d!", 
						usuarioAutenticado.getRegistro(), pedidoEditado.getId())
		);
	}
}
