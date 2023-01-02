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

import br.com.totemAutoatendimento.aplicacao.comanda.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.pedido.BuscarDadosDePedido;
import br.com.totemAutoatendimento.aplicacao.pedido.BuscarPedidosEntregues;
import br.com.totemAutoatendimento.aplicacao.pedido.BuscarPedidosPorData;
import br.com.totemAutoatendimento.aplicacao.pedido.BuscarTodosPedidos;
import br.com.totemAutoatendimento.aplicacao.pedido.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.pedido.DadosFazerPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.DadosRemoverPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.EntregarPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.FazerPedido;
import br.com.totemAutoatendimento.aplicacao.pedido.RemoverPedido;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/pedido")
public class PedidoController {

    @Autowired
    public FazerPedido fazerPedido;

    @Autowired
    public RemoverPedido removerPedido;

    @Autowired
    public EntregarPedido entregarPedido;

    @Autowired
    public BuscarDadosDePedido buscarDadosDePedido;

    @Autowired
    private BuscarPedidosPorData buscarPedidosPorData;

    @Autowired
    private BuscarPedidosEntregues buscarPedidosEntregues;

    @Autowired
    private BuscarTodosPedidos buscarTodosPedidos;

    @PostMapping(value = "/cartao/{cartao}")
    @Operation(summary = "Fazer pedido", description = "Cria um pedido para uma comanda aberta no sistema")
    public ResponseEntity<DadosDeComanda> fazerPedido(@PathVariable String cartao,
            @RequestBody @Valid List<DadosFazerPedido> dados) {
        return ResponseEntity.ok().body(fazerPedido.executar(cartao, dados));
    }

    @DeleteMapping
    @Operation(summary = "Remover pedido", description = "Remove algum pedido de alguma comanda aberta existente")
    public ResponseEntity<DadosDeComanda> removerPedido(@RequestBody @Valid DadosRemoverPedido dados) {
        return ResponseEntity.ok().body(removerPedido.executar(dados));
    }

    @PostMapping(value = "/{id}/entregar")
    @Operation(summary = "Entregar pedido", description = "Atualiza o pedido como entregue")
    public ResponseEntity<DadosDePedido> entregarPedido(@PathVariable Long id) {
        entregarPedido.executar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar pedido", description = "Busca algum pedido existente pelo id")
    public ResponseEntity<DadosDePedido> buscarDadosDePedido(@PathVariable Long id) {
        return ResponseEntity.ok().body(buscarDadosDePedido.executar(id));
    }

    @GetMapping(value = "/data/{dataInicial}/{dataFinal}")
    @Operation(summary = "Buscar pedidos por data", description = "Busca pedidos pela data inicial e final definidas")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosPorData(Pageable paginacao,
            @PathVariable String dataInicial, @PathVariable String dataFinal) {
        return ResponseEntity.ok().body(
                buscarPedidosPorData.executar(paginacao, LocalDate.parse(dataInicial), LocalDate.parse(dataFinal)));
    }

    @GetMapping(value = "/entregues")
    @Operation(summary = "Buscar pedidos entregues", description = "Busca os pedidos que foram entregues")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosEntregues(Pageable paginacao) {
        return ResponseEntity.ok().body(buscarPedidosEntregues.executar(paginacao, true));
    }

    @GetMapping(value = "/nao-entregues")
    @Operation(summary = "Buscar pedidos não entregues", description = "Busca os pedidos que não foram entregues")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosNaoEntregues(Pageable paginacao) {
        return ResponseEntity.ok().body(buscarPedidosEntregues.executar(paginacao, false));
    }

    @GetMapping
    @Operation(summary = "Buscar todos pedidos", description = "Busca todos pedidos existentes")
    public ResponseEntity<Page<DadosDePedido>> buscarTodosPedidos(Pageable paginacao) {
        return ResponseEntity.ok().body(buscarTodosPedidos.executar(paginacao));
    }

}
