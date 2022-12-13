package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;
import java.time.LocalDate;

import javax.transaction.Transactional;
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
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;

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
    private FecharComanda fecharComanda;

    @Autowired
    private ReabrirComanda reabrirComanda;

    @PostMapping
    @Transactional
    public ResponseEntity<Comanda> criarComanda(@RequestBody @Valid DadosCriarComanda dados) {
        Comanda comanda = criarComanda.executar(dados);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comanda.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> removerComanda(@PathVariable Long id){
        removerComanda.executar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DadosDeComanda> buscarDadosDeComanda(@PathVariable Long id){
        return ResponseEntity.ok().body(buscarDadosDeComanda.executar(id));
    }

    @GetMapping(value = "/cartao/{cartao}/aberta")
    public ResponseEntity<DadosDeComanda> buscarComandaAbertaPorCartao(@PathVariable String cartao){
        return ResponseEntity.ok().body(buscarComandaAbertaPorCartao.executar(cartao));
    }

    @GetMapping(value = "/cliente/{id}")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorCliente(Pageable paginacao, @PathVariable Long id){
        return ResponseEntity.ok().body(buscarComandaPorCliente.executar(paginacao, id));
    }

    @GetMapping(value = "/data/{dataInicial}/{dataFinal}")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorData(Pageable paginacao, @PathVariable LocalDate dataInicial, @PathVariable LocalDate dataFinal){
        return ResponseEntity.ok().body(buscarComandaPorData.executar(paginacao, dataInicial, dataFinal));
    }

    @GetMapping(value = "/tipo-pagamento/{tipoPagamento}")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorTipoDePagamento(Pageable paginacao, @PathVariable TipoPagamento tipoPagamento){
        return ResponseEntity.ok().body(buscarComandaPorTipoDePagamento.executar(paginacao, tipoPagamento));
    }

    @GetMapping(value = "/abertas")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasAbertas(Pageable paginacao){
        return ResponseEntity.ok().body(buscarComandasAbertas.executar(paginacao, true));
    }

    @GetMapping(value = "/fechadas")
    public ResponseEntity<Page<DadosDeComanda>> buscarComandasFechadas(Pageable paginacao){
        return ResponseEntity.ok().body(buscarComandasAbertas.executar(paginacao, false));
    }

    @GetMapping
    public ResponseEntity<Page<DadosDeComanda>> buscarTodasComandas(Pageable paginacao){
        return ResponseEntity.ok().body(buscarTodasComandas.executar(paginacao));
    }

    @PostMapping(value = "/{id}/desconto/{desconto}")
    public ResponseEntity<DadosDeComanda> aplicarDescontoEmComanda(@PathVariable Long id, @PathVariable Float desconto){
        return ResponseEntity.ok().body(aplicarDescontoEmComanda.executar(id, desconto));
    }
    
    @PostMapping(value = "/{id}/{tipoPagamento}")
    public ResponseEntity<DadosDeComanda> fecharComanda(@PathVariable Long id, @PathVariable TipoPagamento tipoPagamento){
        return ResponseEntity.ok().body(fecharComanda.executar(id, tipoPagamento));
    }

    @PostMapping(value = "/{id}/reabrir")
    public ResponseEntity<DadosDeComanda> reabrirComanda(@PathVariable Long id){
        return ResponseEntity.ok().body(reabrirComanda.executar(id));
    }

}
