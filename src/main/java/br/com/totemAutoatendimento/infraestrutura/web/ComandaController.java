package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.comanda.AplicarDescontoEmComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaAbertaPorCartao;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaPorCliente;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaPorData;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandaPorTipoDePagamento;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarComandasAbertas;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarDadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscarTodasComandas;
import br.com.totemAutoatendimento.aplicacao.comanda.CriarComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.DadosCriarComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.FecharComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.ReabrirComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoverComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoverDescontoDaComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/comanda")
public class ComandaController {

    @Autowired
    private CriarComanda criarComanda;

    @Autowired
    private RemoverComanda removerComanda;

    @Autowired
    private BuscarDadosDeComanda buscarDadosDeComanda;

    @Autowired
    private BuscarComandaAbertaPorCartao buscarComandaAbertaPorCartao;

    @Autowired
    private BuscarComandaPorCliente buscarComandaPorCliente;

    @Autowired
    private BuscarComandaPorData buscarComandaPorData;

    @Autowired
    private BuscarComandaPorTipoDePagamento buscarComandaPorTipoDePagamento;

    @Autowired
    private BuscarComandasAbertas buscarComandasAbertas;

    @Autowired
    private BuscarTodasComandas buscarTodasComandas;

    @Autowired
    private AplicarDescontoEmComanda aplicarDescontoEmComanda;

    @Autowired
    private RemoverDescontoDaComanda removerDescontoDaComanda;

    @Autowired
    private FecharComanda fecharComanda;

    @Autowired
    private ReabrirComanda reabrirComanda;

    @PostMapping
    @Operation(summary = "Criar comanda", description = "Cria uma comanda no sistema")
    public ResponseEntity<Comanda> criarComanda(@RequestBody @Valid DadosCriarComanda dados) {
        Comanda comanda = criarComanda.executar(dados);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comanda.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Remover comanda", description = "Remove alguma comanda existente")
    public ResponseEntity<Void> removerComanda(@PathVariable Long id) {
        removerComanda.executar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar comanda", description = "Busca alguma comanda pelo id")
    public ResponseEntity<DadosDeComanda> buscarDadosDeComanda(@PathVariable Long id) {
        return ResponseEntity.ok().body(buscarDadosDeComanda.executar(id));
    }

    @GetMapping(value = "/aberta/cartao/{cartao}")
    @Operation(summary = "Buscar comanda pelo cartão", description = "Busca alguma comanda aberta pelo cartão")
    public ResponseEntity<DadosDeComanda> buscarComandaAbertaPorCartao(@PathVariable String cartao) {
        return ResponseEntity.ok().body(buscarComandaAbertaPorCartao.executar(cartao));
    }

    @GetMapping(value = "/cliente/{id}")
    @Operation(summary = "Buscar comandas por cliente", description = "Busca comandas pelo cliente")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorCliente(Pageable paginacao, @PathVariable Long id) {
        return ResponseEntity.ok().body(buscarComandaPorCliente.executar(paginacao, id));
    }

    @GetMapping(value = "/data/{dataInicial}/{dataFinal}")
    @Operation(summary = "Buscar comandas por data", description = "Busca comandas por data inicial e final definidas")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorData(Pageable paginacao,
            @PathVariable String dataInicial, @PathVariable String dataFinal) {
        return ResponseEntity.ok().body(
                buscarComandaPorData.executar(paginacao, LocalDate.parse(dataInicial), LocalDate.parse(dataFinal)));
    }

    @GetMapping(value = "/tipo-pagamento/{tipoPagamento}")
    @Operation(summary = "Buscar comandas por tipo de pagamento", description = "Busca as comandas pelo tipo de pagamento")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorTipoDePagamento(Pageable paginacao,
            @PathVariable String tipoPagamento) {

        return ResponseEntity.ok()
                .body(buscarComandaPorTipoDePagamento.executar(paginacao, TipoPagamento.toEnum(tipoPagamento)));
    }

    @GetMapping(value = "/abertas")
    @Operation(summary = "Buscar comandas abertas", description = "Busca as comandas que estão abertas")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasAbertas(Pageable paginacao) {
        return ResponseEntity.ok().body(buscarComandasAbertas.executar(paginacao, true));
    }

    @GetMapping(value = "/fechadas")
    @Operation(summary = "Buscar comandas fechadas", description = "Busca as comandas que estão fechadas")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasFechadas(Pageable paginacao) {
        return ResponseEntity.ok().body(buscarComandasAbertas.executar(paginacao, false));
    }

    @GetMapping
    @Operation(summary = "Buscar todas comandas", description = "Busca todas as comandas existntes")
    public ResponseEntity<Page<DadosDeComanda>> buscarTodasComandas(Pageable paginacao) {
        return ResponseEntity.ok().body(buscarTodasComandas.executar(paginacao));
    }

    @PostMapping(value = "/{id}/aplicar-desconto/{desconto}")
    @Operation(summary = "Aplicar desconto", description = "Aplica um desconto percentual na comanda")
    public ResponseEntity<DadosDeComanda> aplicarDescontoEmComanda(@PathVariable Long id,
            @PathVariable Float desconto) {
        return ResponseEntity.ok().body(aplicarDescontoEmComanda.executar(id, desconto));
    }

    @PostMapping(value = "/{id}/remover-desconto")
    @Operation(summary = "Remover desconto", description = "Remove o desconto da comanda")
    public ResponseEntity<DadosDeComanda> removerDescontoDaComanda(@PathVariable Long id) {
        return ResponseEntity.ok().body(removerDescontoDaComanda.executar(id));
    }

    @PostMapping(value = "/{id}/{tipoPagamento}")
    @Operation(summary = "Fechar comanda", description = "Fecha alguma comanda aberta")
    public ResponseEntity<DadosDeComanda> fecharComanda(@PathVariable Long id, @PathVariable String tipoPagamento) {
        return ResponseEntity.ok().body(fecharComanda.executar(id, TipoPagamento.toEnum(tipoPagamento)));
    }

    @PostMapping(value = "/{id}/reabrir")
    @Operation(summary = "Reabrir comanda", description = "Reabre alguma comanda fechada")
    public ResponseEntity<DadosDeComanda> reabrirComanda(@PathVariable Long id) {
        return ResponseEntity.ok().body(reabrirComanda.executar(id));
    }

}
