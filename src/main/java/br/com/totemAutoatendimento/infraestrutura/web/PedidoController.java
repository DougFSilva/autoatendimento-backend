package br.com.totemAutoatendimento.infraestrutura.web;

import java.time.LocalDate;

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
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarDadosDePedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarPedidosEntregues;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarPedidosPorData;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.BuscarTodosPedidos;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.DadosFazerPedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.EntregarPedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.FazerPedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.RemoverPedido;

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

    @PostMapping
    public ResponseEntity<DadosDeComanda> fazerPedido(@RequestBody @Valid DadosFazerPedido dados){
        return ResponseEntity.ok().body(fazerPedido.executar(dados));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<DadosDeComanda> removerPedido(@PathVariable Long id){
        return ResponseEntity.ok().body(removerPedido.executar(id));
    }

    @PostMapping(value = "/{id}/entregar")
    public ResponseEntity<DadosDePedido> entregarPedido(@PathVariable Long id){
        entregarPedido.executar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DadosDePedido> buscarDadosDePedido(@PathVariable Long id){
        return ResponseEntity.ok().body(buscarDadosDePedido.executar(id));
    }

    @GetMapping(value = "/data/{dataInicial}/{dataFinal}")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosPorData(Pageable paginacao, @PathVariable LocalDate dataInicial, @PathVariable LocalDate dataFinal){
        return ResponseEntity.ok().body(buscarPedidosPorData.executar(paginacao, dataInicial, dataFinal));
    }

    @GetMapping(value = "/entregues")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosEntregues(Pageable paginacao){
        return ResponseEntity.ok().body(buscarPedidosEntregues.executar(paginacao, true));
    }

    @GetMapping(value = "/nao-entregues")
    public ResponseEntity<Page<DadosDePedido>> buscarPedidosNaoEntregues(Pageable paginacao){
        return ResponseEntity.ok().body(buscarPedidosEntregues.executar(paginacao, false));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDePedido>> buscarTodosPedidos(Pageable paginacao){
        return ResponseEntity.ok().body(buscarTodosPedidos.executar(paginacao));
    }

}
