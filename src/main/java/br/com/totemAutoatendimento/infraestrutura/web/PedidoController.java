package br.com.totemAutoatendimento.infraestrutura.web;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.pedido.BuscaDadosDePedidos;
import br.com.totemAutoatendimento.aplicacao.pedido.EntregaPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.FazPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.RemovePedido;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosFazerPedido;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {

    @Autowired
    public FazPedido fazPedido;

    @Autowired
    public RemovePedido removePedido;

    @Autowired
    public EntregaPedido entregaPedido;

    @Autowired
    public BuscaDadosDePedidos buscaDadosDePedidos;


    @PostMapping(value = "/cartao/{cartao}")
    @Operation(summary = "Fazer pedido", description = "Cria um pedido para uma comanda aberta no sistema")
    public ResponseEntity<DadosDeComanda> fazerPedido(@PathVariable String cartao,
            @RequestBody @Valid List<DadosFazerPedido> dados) {
        return ResponseEntity.ok().body(fazPedido.fazer(cartao, dados));
    }

    @DeleteMapping(value = "/{id}/cartao/{cartao}")
    @Operation(summary = "Remover pedido", description = "Remove algum pedido de alguma comanda aberta existente")
    public ResponseEntity<DadosDeComanda> removerPedido(@PathVariable Long id, @PathVariable String cartao) {
        return ResponseEntity.ok().body(removePedido.remover(id, cartao));
    }

    @PostMapping(value = "/{id}/entregar")
    @Operation(summary = "Entregar pedido", description = "Atualiza o pedido como entregue")
    public ResponseEntity<DadosDePedido> entregarPedido(@PathVariable Long id) {
        entregaPedido.entregar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar pedido", description = "Busca algum pedido existente pelo id")
    public ResponseEntity<DadosDePedido> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok().body(buscaDadosDePedidos.buscarPeloId(id));
    }
    
    @GetMapping(value = "/comanda/{comandaId}")
    @Operation(summary = "Buscar pedidos pela comanda", description = "Busca pedidos existente pelo comanda")
    public ResponseEntity<List<DadosDePedido>> buscarPedidosPelaComanda(@PathVariable Long comandaId) {
        return ResponseEntity.ok().body(buscaDadosDePedidos.buscarPelaComanda(comandaId));
    }

    @GetMapping(value = "/data/{dataInicial}/{dataFinal}")
    @Operation(summary = "Buscar pedidos por data", description = "Busca pedidos pela data inicial e final definidas")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosPelaData(Pageable paginacao,
            @PathVariable String dataInicial, @PathVariable String dataFinal) {
        return ResponseEntity.ok().body(
        		buscaDadosDePedidos.buscarPorData(paginacao, LocalDate.parse(dataInicial), LocalDate.parse(dataFinal)));
    }

    @GetMapping(value = "/entregues")
    @Operation(summary = "Buscar pedidos entregues", description = "Busca os pedidos que foram entregues")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosEntregues(Pageable paginacao) {
        return ResponseEntity.ok().body(buscaDadosDePedidos.buscarEntregues(paginacao, true));
    }

    @GetMapping(value = "/nao-entregues")
    @Operation(summary = "Buscar pedidos não entregues", description = "Busca os pedidos que não foram entregues")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosNaoEntregues(Pageable paginacao) {
        return ResponseEntity.ok().body(buscaDadosDePedidos.buscarEntregues(paginacao, false));
    }

    @GetMapping
    @Operation(summary = "Buscar todos pedidos", description = "Busca todos pedidos existentes")
    public ResponseEntity<Page<DadosDePedido>> buscarTodosPedidos(Pageable paginacao) {
        return ResponseEntity.ok().body(buscaDadosDePedidos.buscarTodos(paginacao));
    }

}
