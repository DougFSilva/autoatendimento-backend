package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.Avaliacao.BuscaDadosDeAvaliacao;
import br.com.totemAutoatendimento.aplicacao.Avaliacao.CriaAvaliacao;
import br.com.totemAutoatendimento.aplicacao.Avaliacao.DeletaAvaliacao;
import br.com.totemAutoatendimento.aplicacao.Avaliacao.dto.DadosCriarAvaliacao;
import br.com.totemAutoatendimento.aplicacao.Avaliacao.dto.DadosDeAvaliacao;
import br.com.totemAutoatendimento.dominio.avaliacao.Avaliacao;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/avaliacao")
public class AvaliacaoController {

	@Autowired
	private CriaAvaliacao criaAvaliacao;

	@Autowired
	private DeletaAvaliacao deletaAvaliacao;

	@Autowired
	private BuscaDadosDeAvaliacao buscaDadosDeAvaliacao;

	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping
	@CacheEvict(value = "buscarTodasAvaliacoes", allEntries = true)
	@Operation(summary = "Criar avaliação", description = "Cria uma nova avaliação no sistema")
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody @Valid DadosCriarAvaliacao dados) {
		Avaliacao avaliacao = criaAvaliacao.criar(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(avaliacao.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "buscarTodasAvaliacoes", allEntries = true)
	@Operation(summary = "Deletar avaliação", description = "Deleta uma avaliação existente no sistema")
	public ResponseEntity<Void> deletarAvaliacao(@PathVariable Long id) {
		deletaAvaliacao.deletar(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/data/{dataInicial}/{dataFinal}")
	@Operation(summary = "Buscar avaliações pela data", description = "Busca todas avaliações existente no sistema pela data")
	public ResponseEntity<Page<DadosDeAvaliacao>> buscarAvaliacoesPelaData(Pageable paginacao, @PathVariable String dataInicial,
			@PathVariable String dataFinal) {
		Page<DadosDeAvaliacao> dadosDeAvaliacoes = buscaDadosDeAvaliacao.buscarTodasPelaData(
				paginacao, 
				LocalDateTime.of(LocalDate.parse(dataInicial), LocalTime.MIN), 
				LocalDateTime.of(LocalDate.parse(dataFinal), LocalTime.MAX), 
				usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeAvaliacoes);
	}
	
	@GetMapping
	@Operation(summary = "Buscar todas avaliações", description = "Busca todas avaliações existente no sistema")
	@Cacheable("buscarTodasAvaliacoes")
	public ResponseEntity<Page<DadosDeAvaliacao>> buscarTodasAvaliacoes(Pageable paginacao) {
		Page<DadosDeAvaliacao> dadosDeAvaliacoes = buscaDadosDeAvaliacao.buscarTodas(paginacao, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeAvaliacoes);
	}

	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}
}
