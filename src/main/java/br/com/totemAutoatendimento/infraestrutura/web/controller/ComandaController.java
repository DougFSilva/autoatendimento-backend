package br.com.totemAutoatendimento.infraestrutura.web.controller;

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
import br.com.totemAutoatendimento.aplicacao.comanda.AbreComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.FechaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.ReabreComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.DeletaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.RemoveDescontoDaComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosCriarComanda;
import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/comanda")
@SecurityRequirement(name = "api-security")
public class ComandaController {

	@Autowired
	private AbreComanda abreComanda;

	@Autowired
	private DeletaComanda removeComanda;

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
	
	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping
	@Operation(summary = "Criar comanda", description = "Cria uma comanda no sistema")
	public ResponseEntity<Comanda> criarComanda(@RequestBody @Valid DadosCriarComanda dados) {
		Comanda comanda = abreComanda.abrir(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(comanda.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Remover comanda", description = "Remove alguma comanda existente")
	public ResponseEntity<Void> removerComanda(@PathVariable Long id) {
		removeComanda.deletar(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar comanda", description = "Busca alguma comanda pelo id")
	public ResponseEntity<DadosDeComanda> buscarComanda(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarPeloId(id, usuarioAutenticado()));
	}

	@GetMapping("/aberta/cartao/{cartao}")
	@Operation(summary = "Buscar comanda pelo cartão", description = "Busca alguma comanda aberta pelo cartão")
	public ResponseEntity<DadosDeComanda> buscarComandaAbertaPeloCartao(@PathVariable String cartao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarAbertaPeloCartao(cartao, usuarioAutenticado()));
	}

	@GetMapping("/cliente/{id}")
	@Operation(summary = "Buscar comandas por cliente", description = "Busca comandas pelo cliente")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasPeloCliente(Pageable paginacao, @PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarPeloCliente(paginacao, id, usuarioAutenticado()));
	}

	@GetMapping("/data/{dataInicial}/{dataFinal}")
	@Operation(summary = "Buscar comandas por data", description = "Busca comandas por data inicial e final definidas")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasPelaData(Pageable paginacao,
			@PathVariable String dataInicial, @PathVariable String dataFinal) {
		return ResponseEntity.ok().body(
				buscaDadosDeComandas.buscarPelaData(paginacao, LocalDate.parse(dataInicial), LocalDate.parse(dataFinal), usuarioAutenticado()));
	}

	@GetMapping("/tipo-pagamento/{tipoPagamento}")
	@Operation(summary = "Buscar comandas por tipo de pagamento", description = "Busca as comandas pelo tipo de pagamento")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasPeloTipoDePagamento(Pageable paginacao,
			@PathVariable String tipoPagamento) {
		return ResponseEntity.ok()
				.body(buscaDadosDeComandas.buscarPeloTipoDePagamento(paginacao, TipoPagamento.toEnum(tipoPagamento), usuarioAutenticado()));
	}

	@GetMapping("/abertas")
	@Operation(summary = "Buscar comandas abertas", description = "Busca as comandas que estão abertas")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasAbertas(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarAbertas(paginacao, true, usuarioAutenticado()));
	}

	@GetMapping("/fechadas")
	@Operation(summary = "Buscar comandas fechadas", description = "Busca as comandas que estão fechadas")
	public ResponseEntity<Page<DadosDeComanda>> buscarComandasFechadas(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarAbertas(paginacao, false, usuarioAutenticado()));
	}

	@GetMapping
	@Operation(summary = "Buscar todas comandas", description = "Busca todas as comandas existntes")
	public ResponseEntity<Page<DadosDeComanda>> buscarTodasComandas(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeComandas.buscarTodas(paginacao, usuarioAutenticado()));
	}

	@PostMapping("/{id}/aplicar-desconto/{desconto}")
	@Operation(summary = "Aplicar desconto", description = "Aplica um desconto percentual na comanda")
	public ResponseEntity<DadosDeComanda> aplicarDescontoEmComanda(@PathVariable Long id,
			@PathVariable Float desconto) {
		return ResponseEntity.ok().body(aplicaDescontoEmComanda.aplicar(id, desconto, usuarioAutenticado()));
	}

	@PostMapping("/{id}/remover-desconto")
	@Operation(summary = "Remover desconto", description = "Remove o desconto da comanda")
	public ResponseEntity<DadosDeComanda> removerDescontoDaComanda(@PathVariable Long id) {
		return ResponseEntity.ok().body(removeDescontoDaComanda.remover(id, usuarioAutenticado()));
	}

	@PostMapping("/{id}/{tipoPagamento}")
	@Operation(summary = "Fechar comanda", description = "Fecha alguma comanda aberta")
	public ResponseEntity<DadosDeComanda> fecharComanda(@PathVariable Long id, @PathVariable String tipoPagamento) {
		return ResponseEntity.ok().body(fechaComanda.fechar(id, TipoPagamento.toEnum(tipoPagamento), usuarioAutenticado()));
	}

	@PostMapping("/{id}/reabrir")
	@Operation(summary = "Reabrir comanda", description = "Reabre alguma comanda fechada")
	public ResponseEntity<DadosDeComanda> reabrirComanda(@PathVariable Long id) {
		return ResponseEntity.ok().body(reabreComanda.reabrir(id, usuarioAutenticado()));
	}
	
	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}

}
