package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.pedido.BuscaDadosDePedidos;
import br.com.totemAutoatendimento.aplicacao.pedido.DeletaPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.EntregaPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.FazPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosFazerPedido;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/pedido")
@SecurityRequirement(name = "api-security")
public class PedidoController {

	@Autowired
	public FazPedido fazPedido;

	@Autowired
	public DeletaPedido deletaPedido;

	@Autowired
	public EntregaPedido entregaPedido;

	@Autowired
	public BuscaDadosDePedidos buscaDadosDePedidos;
	
	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping("/cartao/{cartao}")
	@Operation(summary = "Fazer pedido", description = "Faz um pedido para uma comanda aberta no sistema")
	public ResponseEntity<DadosDeComanda> fazerPedido(@PathVariable String cartao,
			@RequestBody @Valid List<DadosFazerPedido> dados) {
		DadosDeComanda dadosDeComanda = fazPedido.fazer(cartao, dados, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeComanda);
	}

	@DeleteMapping("/{id}/cartao/{cartao}")
	@Operation(summary = "Deletar pedido", description = "Deleta um pedido de alguma comanda aberta existente. "
			+ "Não é possível remover pedido já entregue")
	public ResponseEntity<DadosDeComanda> deletarPedido(@PathVariable Long id, @PathVariable String cartao) {
		DadosDeComanda dadosDeComanda = deletaPedido.deletar(id, cartao, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeComanda);
	}

	@PatchMapping("/{id}/entregar")
	@Operation(summary = "Entregar pedido", description = "Atualiza o pedido como entregue")
	public ResponseEntity<DadosDePedido> entregarPedido(@PathVariable Long id) {
		entregaPedido.entregar(id, usuarioAutenticado());
		return ResponseEntity.ok().build();
	}
	
	@PatchMapping("/{id}/cancelar-entrega")
	@Operation(summary = "Cancelar entrega de pedido", description = "Atualiza o pedido como não entregue")
	public ResponseEntity<DadosDePedido> CancelarEntregaDePedido(@PathVariable Long id) {
		entregaPedido.cancelarEntrega(id, usuarioAutenticado());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar pedido", description = "Busca um pedido existente no sistema pelo id")
	public ResponseEntity<DadosDePedido> buscarPedido(@PathVariable Long id) {
		DadosDePedido dadosDePedido = buscaDadosDePedidos.buscarPeloId(id, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDePedido);
	}

	@GetMapping("/comanda/{comandaId}")
	@Operation(summary = "Buscar pedidos pela comanda", description = "Busca pedidos existente pelo comanda")
	public ResponseEntity<List<DadosDePedido>> buscarPedidosPelaComanda(@PathVariable Long comandaId) {
		List<DadosDePedido> dadosDePedidos = buscaDadosDePedidos.buscarPelaComanda(comandaId, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDePedidos);
	}

	@GetMapping("/data/{dataInicial}/{dataFinal}")
	@Operation(summary = "Buscar pedidos por data", description = "Busca pedidos pela data inicial e final definidas")
	public ResponseEntity<Page<DadosDePedido>> buscarPedidosPelaData(Pageable paginacao,
			@PathVariable String dataInicial, @PathVariable String dataFinal) {
		Page<DadosDePedido> dadosDePedidos = buscaDadosDePedidos.buscarPorData(
				paginacao, 
				LocalDate.parse(dataInicial), 
				LocalDate.parse(dataFinal), 
				usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDePedidos);
	}

	@GetMapping("/entregues")
	@Operation(summary = "Buscar pedidos entregues", description = "Busca os pedidos que foram entregues")
	public ResponseEntity<Page<DadosDePedido>> buscarPedidosEntregues(Pageable paginacao) {
		Page<DadosDePedido> dadosDePedidos = buscaDadosDePedidos.buscarEntregues(paginacao, true, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDePedidos);
	}

	@GetMapping("/nao-entregues")
	@Operation(summary = "Buscar pedidos não entregues", description = "Busca os pedidos que não foram entregues")
	public ResponseEntity<Page<DadosDePedido>> buscarPedidosNaoEntregues(Pageable paginacao) {
		Page<DadosDePedido> dadosDePedidos = buscaDadosDePedidos.buscarEntregues(paginacao, false, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDePedidos);
	}

	@GetMapping
	@Operation(summary = "Buscar todos pedidos", description = "Busca todos pedidos existentes")
	public ResponseEntity<Page<DadosDePedido>> buscarTodosPedidos(Pageable paginacao) {
		Page<DadosDePedido> dadosDePedidos = buscaDadosDePedidos.buscarTodos(paginacao, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDePedidos);
	}
	
	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}

}
