package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpSession;
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

import br.com.totemAutoatendimento.aplicacao.funcionario.BuscaDadosDeFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.CadastraFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.EditaFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.DeletaFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.dto.DadosCriarFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.dto.DadosDeFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.dto.DadosEditarFuncionario;
import br.com.totemAutoatendimento.aplicacao.usuario.AlteraSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosAlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

	@Autowired
	private CadastraFuncionario cadastraFuncionario;

	@Autowired
	private DeletaFuncionario deletaFuncionario;

	@Autowired
	private EditaFuncionario editaFuncionario;

	@Autowired
	private BuscaDadosDeFuncionario buscaDadosDeFuncionario;

	@Autowired
	private AlteraSenhaDeUsuario alteraSenhaDeUsuario;

	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping
	@CacheEvict(value = "buscarTodosFuncionario", allEntries = true)
	@Operation(summary = "Cadastrar funcionário", description = "Cadastra um funcionário no sistema")
	public ResponseEntity<Funcionario> cadastrarFuncionario(@Valid @RequestBody DadosCriarFuncionario dados) {
		Funcionario funcionario = cadastraFuncionario.cadastrar(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(funcionario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = "buscarTodosFuncionario", allEntries = true)
	@Operation(summary = "Deletar funcionário", description = "Deleta um funcionário cadastrado no sistema")
	public ResponseEntity<Void> deletarFuncionario(@PathVariable Long id) {
		deletaFuncionario.deletar(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@CacheEvict(value = "buscarTodosFuncionario", allEntries = true)
	@Operation(summary = "Editar funcionário", description = "Edita um funcionário cadastrado no sistema")
	public ResponseEntity<DadosDeFuncionario> editarFuncionario(@PathVariable Long id,
			@RequestBody @Valid DadosEditarFuncionario dados) {
		DadosDeFuncionario dadosDeFuncionario = editaFuncionario.editar(id, dados, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeFuncionario);
	}
	
	@PatchMapping("/alterar-senha")
	@Operation(summary = "Alterar senha de funcionário", description = "Altera a senha de um funcionário "
			+ "logado no sistema")
	public ResponseEntity<Void> alterarSenhaDeUsuario(@RequestBody @Valid DadosAlterarSenhaDeUsuario dados, HttpSession session) {
		alteraSenhaDeUsuario.alterar(dados, usuarioAutenticado());
		session.invalidate();
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar dados de funcionário pelo id", description = "Busca um funcionário "
			+ "cadastrado no sistema pelo id")
	public ResponseEntity<DadosDeFuncionario> buscarFuncionarioPeloId(@PathVariable Long id) {
		DadosDeFuncionario dadosDeFuncionario = buscaDadosDeFuncionario.buscarPeloId(id, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeFuncionario);
	}

	@GetMapping("/matricula/{matricula}")
	@Operation(summary = "Buscar dados de funcionário pela matricula", description = "Busca um funcionário "
			+ "cadastrado no sistema pela matricula")
	public ResponseEntity<DadosDeFuncionario> buscarFuncionarioPelaMatricula(@PathVariable String matricula) {
		DadosDeFuncionario dadosDeFuncionario = buscaDadosDeFuncionario.buscarPelaMatricuka(matricula, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeFuncionario);
	}
	
	@GetMapping("/usuario/{usuarioId}")
	@Operation(summary = "Buscar dados de funcionário pelo usuário", description = "Busca um funcionário "
			+ "cadastrado no sistema pelo id do usuário pertencente a esse funcionário")
	public ResponseEntity<DadosDeFuncionario> buscarFuncionarioPeloUsuario(@PathVariable Long usuarioId) {
		DadosDeFuncionario dadosDeFuncionario = buscaDadosDeFuncionario.buscarPeloUsuario(usuarioId, usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeFuncionario);
	}

	@GetMapping
	@Cacheable("buscarTodosFuncionario")
	@Operation(summary = "Buscar os dados de todos funcionário", description = "Busca todos os "
			+ "funcionários cadastrados no sistema")
	public ResponseEntity<List<DadosDeFuncionario>> buscarTodosFuncionario() {
		List<DadosDeFuncionario> dadosDeFuncionarios = buscaDadosDeFuncionario.buscarTodos(usuarioAutenticado());
		return ResponseEntity.ok().body(dadosDeFuncionarios);
	}

	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}
}
