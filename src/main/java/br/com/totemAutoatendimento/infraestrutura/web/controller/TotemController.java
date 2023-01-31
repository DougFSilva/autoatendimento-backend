package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.totem.AlteraSenhaDeTotem;
import br.com.totemAutoatendimento.aplicacao.totem.BuscaDadosDeTotem;
import br.com.totemAutoatendimento.aplicacao.totem.CadastraTotem;
import br.com.totemAutoatendimento.aplicacao.totem.EditaTotem;
import br.com.totemAutoatendimento.aplicacao.totem.DeletaTotem;
import br.com.totemAutoatendimento.aplicacao.totem.dto.DadosCriarTotem;
import br.com.totemAutoatendimento.aplicacao.totem.dto.DadosDeTotem;
import br.com.totemAutoatendimento.aplicacao.totem.dto.DadosEditarTotem;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosAlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/totem")
public class TotemController {

	@Autowired
	private CadastraTotem criaTotem;
	
	@Autowired
	private DeletaTotem removeTotem;
	
	@Autowired
	private EditaTotem editaTotem;
	
	@Autowired
	private BuscaDadosDeTotem buscaDadosDeTotem;
	
	@Autowired
	private AlteraSenhaDeTotem alteraSenhaDeTotem;
	
	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;
	
	@PostMapping
	@CacheEvict(value = "buscarTodosTotens", allEntries = true)
	@Operation(summary = "Criar Totem", description = "Cria um Totem no sistema")
	public ResponseEntity<Totem> criarTotem(@RequestBody @Valid DadosCriarTotem dados) {
		Totem totem = criaTotem.cadastrar(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(totem.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{id}")
	@CacheEvict(value = "buscarTodosTotens", allEntries = true)
	@Operation(summary = "Remover Totem", description = "Remove um Totem cadastrado no sistema")
	public ResponseEntity<Void> removerTotem(@PathVariable Long id) {
		removeTotem.deletar(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	@CacheEvict(value = "buscarTodosTotens", allEntries = true)
	@Operation(summary = "Editar Totem", description = "Edita um Totem cadastrado no sistema")
	public ResponseEntity<DadosDeTotem> editarTotem(@PathVariable Long id, @RequestBody @Valid DadosEditarTotem dados) {
		DadosDeTotem dadosDeTotem = editaTotem.editar(id, dados, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeTotem);
	}
	
	@PatchMapping("/{id}/alterar-senha")
	@Operation(summary = "Alterar senha de Totem", description = "Altera senha de um Totem cadastrado no sistema")
	public ResponseEntity<Void> alterarSenhaDeTotem(@PathVariable Long id, @RequestBody @Valid DadosAlterarSenhaDeUsuario dados) {
		alteraSenhaDeTotem.alterar(id, dados, usuarioAutenticado());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar Totem pelo id", description = "Busca os dados de um Totem cadastrado "
			+ "no sistema pelo id")
	public ResponseEntity<DadosDeTotem> buscarTotemPeloId(@PathVariable Long id) {
		DadosDeTotem dadosDeTotem = buscaDadosDeTotem.buscarPeloId(id, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeTotem);
	}
	
	@GetMapping("/registro/{registro}")
	@Operation(summary = "Buscar Totem pelo identificador", description = "Busca os dados de um Totem "
			+ "cadastrado no sistema pelo identificador")
	public ResponseEntity<DadosDeTotem> buscarTotemPeloIdentificador(@PathVariable String identificador) {
		DadosDeTotem dadosDeTotem = buscaDadosDeTotem.buscarPeloIdentificador(identificador, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeTotem);
	}
	
	@GetMapping
	@Cacheable("buscarTodosTotens")
	@Operation(summary = "Buscar todos os Totens", description = "Busca os dados de todos os "
			+ "Totens cadastrado no sistema")
	public ResponseEntity<List<DadosDeTotem>> buscarTodosTotens() {
		List<DadosDeTotem> dadosDeTotens = buscaDadosDeTotem.buscarTodos(usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeTotens);
	}
	
	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}
}
