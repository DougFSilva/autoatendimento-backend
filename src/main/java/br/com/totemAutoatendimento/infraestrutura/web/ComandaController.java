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

import br.com.totemAutoatendimento.aplicacao.comanda.AplicaDescontoEmComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.BuscaDadosDeComandas;
import br.com.totemAutoatendimento.aplicacao.comanda.CriaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.FechaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.ReabreComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoveComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoveDescontoDaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosCriarComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/comanda")
public class ComandaController {

	@Autowired
	private CriaComanda criaComanda;

	@Autowired
	private RemoveComanda removeComanda;

	@Autowired
	private BuscaDadosDeComandas buscaDadosDeComandas;

	@Autowired
	private AplicaDescontoEmComanda aplicaDescontoEmComanda;

	@Autowired
	private RemoveDescontoDaComanda removeDescontoDaComanda;

	@Autowired
	private FechaComanda fechaComanda;

	@Autowired
	private ReabreComanda reabreComanda;

	@PostMapping
	@Operation(summary = "Criar comanda", description = "Cria uma comanda no sistema")
	public ResponseEntity<Comanda> criarComanda(@RequestBody @Valid DadosCriarComanda dados) {
		Comanda comanda = criaComanda.criar(dados);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comanda.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Remover comanda", description = "Remove alguma comanda existente")
	public ResponseEntity<Void> removerComanda(@PathVariable Long id) {
		removeComanda.remover(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar comanda", description = "Busca alguma comanda pelo id")
	public ResponseEntity<DadosDeComanda> buscarDadosDeComanda(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarPeloId(id));
	}

	@GetMapping(value = "/aberta/cartao/{cartao}")
	@Operation(summary = "Buscar comanda pelo cartão", description = "Busca alguma comanda aberta pelo cartão")
	public ResponseEntity<DadosDeComanda> buscarComandaAbertaPorCartao(@PathVariable String cartao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarAbertasPeloCartao(cartao));
	}

	@GetMapping(value = "/cliente/{id}")
	@Operation(summary = "Buscar comandas por cliente", description = "Busca comandas pelo cliente")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorCliente(Pageable paginacao, @PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarPeloCliente(paginacao, id));
	}

	@GetMapping(value = "/data/{dataInicial}/{dataFinal}")
	@Operation(summary = "Buscar comandas por data", description = "Busca comandas por data inicial e final definidas")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorData(Pageable paginacao,
			@PathVariable String dataInicial, @PathVariable String dataFinal) {
		return ResponseEntity.ok().body(
				buscaDadosDeComandas.buscarPelaData(paginacao, LocalDate.parse(dataInicial), LocalDate.parse(dataFinal)));
	}

	@GetMapping(value = "/tipo-pagamento/{tipoPagamento}")
	@Operation(summary = "Buscar comandas por tipo de pagamento", description = "Busca as comandas pelo tipo de pagamento")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasPorTipoDePagamento(Pageable paginacao,
			@PathVariable String tipoPagamento) {

		return ResponseEntity.ok()
				.body(buscaDadosDeComandas.buscarPeloTipoDePagamento(paginacao, TipoPagamento.toEnum(tipoPagamento)));
	}

	@GetMapping(value = "/abertas")
	@Operation(summary = "Buscar comandas abertas", description = "Busca as comandas que estão abertas")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasAbertas(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarAbertas(paginacao, true));
	}

	@GetMapping(value = "/fechadas")
	@Operation(summary = "Buscar comandas fechadas", description = "Busca as comandas que estão fechadas")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasFechadas(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarAbertas(paginacao, false));
	}

	@GetMapping
	@Operation(summary = "Buscar todas comandas", description = "Busca todas as comandas existntes")
	public ResponseEntity<Page<DadosDeComanda>> buscarTodasComandas(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarTodas(paginacao));
	}

	@PostMapping(value = "/{id}/aplicar-desconto/{desconto}")
	@Operation(summary = "Aplicar desconto", description = "Aplica um desconto percentual na comanda")
	public ResponseEntity<DadosDeComanda> aplicarDescontoEmComanda(@PathVariable Long id,
			@PathVariable Float desconto) {
		return ResponseEntity.ok().body(aplicaDescontoEmComanda.aplicar(id, desconto));
	}

	@PostMapping(value = "/{id}/remover-desconto")
	@Operation(summary = "Remover desconto", description = "Remove o desconto da comanda")
	public ResponseEntity<DadosDeComanda> removerDescontoDaComanda(@PathVariable Long id) {
		return ResponseEntity.ok().body(removeDescontoDaComanda.remover(id));
	}

	@PostMapping(value = "/{id}/{tipoPagamento}")
	@Operation(summary = "Fechar comanda", description = "Fecha alguma comanda aberta")
	public ResponseEntity<DadosDeComanda> fecharComanda(@PathVariable Long id, @PathVariable String tipoPagamento) {
		return ResponseEntity.ok().body(fechaComanda.fechar(id, TipoPagamento.toEnum(tipoPagamento)));
	}

	@PostMapping(value = "/{id}/reabrir")
	@Operation(summary = "Reabrir comanda", description = "Reabre alguma comanda fechada")
	public ResponseEntity<DadosDeComanda> reabrirComanda(@PathVariable Long id) {
		return ResponseEntity.ok().body(reabreComanda.reabrir(id));
	}

}
