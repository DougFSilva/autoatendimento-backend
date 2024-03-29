package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.anotacao.BuscaDadosDeAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.CriaAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.EditaAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.DeletaAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosCriarOuEditarAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.dto.DadosDeAnotacao;
import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/anotacao")
@SecurityRequirement(name = "api-security")
public class AnotacaoController {

	@Autowired
	private CriaAnotacao criaAnotacao;

	@Autowired
	private DeletaAnotacao deletaAnotacao;

	@Autowired
	private EditaAnotacao editaAnotacao;

	@Autowired
	private BuscaDadosDeAnotacao buscaDadosDeAnotacao;

	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping
	@CacheEvict(value = "buscarTodasAnotacoes", allEntries = true)
	@Operation(summary = "Criar anotação", description = "Cria uma nova anotação no sistema")
	public ResponseEntity<Anotacao> criarAnotacao(@RequestBody @Valid DadosCriarOuEditarAnotacao dados) {
		System.out.println(dados);
		Anotacao anotacao = criaAnotacao.criar(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(anotacao.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "buscarTodasAnotacoes", allEntries = true)
	@Operation(summary = "Deletar anotação", description = "Deleta uma anotações existente no sistema")
	public ResponseEntity<Void> deletarAnotacao(@PathVariable Long id) {
		deletaAnotacao.deletar(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@CacheEvict(value = "buscarTodasAnotacoes", allEntries = true)
	@Operation(summary = "Editar anotação", description = "Edita uma anotações existente no sistema")
	public ResponseEntity<DadosDeAnotacao> editarAnotacao(@PathVariable Long id,
			@RequestBody @Valid DadosCriarOuEditarAnotacao dados) {
		DadosDeAnotacao dadosDeAnotacao = editaAnotacao.editar(id, dados, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeAnotacao);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar anotação pelo id", description = "Busca uma anotação existente no sistema pelo id")
	public ResponseEntity<DadosDeAnotacao> buscarAnotacaoPeloId(@PathVariable Long id) {
		DadosDeAnotacao dadosDeAnotacao = buscaDadosDeAnotacao.buscarPeloId(id, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeAnotacao);
	}

	@GetMapping("/data/{dataInicial}/{dataFinal}")
	@Operation(summary = "Buscar anotações pela data", description = "Busca anotações por uma data inicial e uma data final")
	public ResponseEntity<Page<DadosDeAnotacao>> buscarAnotacoesPelaData(Pageable paginacao,
			@PathVariable String dataInicial, @PathVariable String dataFinal) {
		Page<DadosDeAnotacao> dadosDeAnotacoes = buscaDadosDeAnotacao.buscarPelaData(paginacao,
				LocalDate.parse(dataInicial), LocalDate.parse(dataFinal), usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeAnotacoes);
	}

	@GetMapping
	@Cacheable("buscarTodasAnotacoes")
	@Operation(summary = "Buscar todas anotações", description = "Busca todas anotações existentes")
	public ResponseEntity<Page<DadosDeAnotacao>> buscarTodasAnotacoes(Pageable paginacao) {
		Page<DadosDeAnotacao> dadosDeAnotacoes = buscaDadosDeAnotacao.buscarTodas(paginacao, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeAnotacoes);
	}

	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}

}
