package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.comanda.BuscarDadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.CriarComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.DadosCriarComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.DadosFecharComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.FecharComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoverComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.DadosFazerPedido;
import br.com.totemAutoatendimento.aplicacao.comanda.pedido.FazerPedido;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;

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
    private FazerPedido fazerPedido;

    @Autowired
    private FecharComanda fecharComanda;

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

    @PostMapping(value = "/fazer-pedido")
    public ResponseEntity<DadosDeComanda> fazerPedido(@RequestBody @Valid DadosFazerPedido dados){
        return ResponseEntity.ok().body(fazerPedido.executar(dados));
    }

    @PostMapping(value = "/fechar")
    public ResponseEntity<DadosDeComanda> fecharComanda(@RequestBody @Valid DadosFecharComanda dados){
        return ResponseEntity.ok().body(fecharComanda.executar(dados));
    }

}
