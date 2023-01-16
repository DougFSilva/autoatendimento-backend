package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.totemAutoatendimento.aplicacao.usuario.AlteraSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.BuscaDadosDeUsuarios;
import br.com.totemAutoatendimento.aplicacao.usuario.CriaUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.EditaUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.RemoveUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosAlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosCriarUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosEditarUsuario;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private CriaUsuario criaUsuario;

	@Autowired
	private RemoveUsuario removeUsuario;

	@Autowired
	private EditaUsuario editaUsuario;

	@Autowired
	private AlteraSenhaDeUsuario alteraSenhaDeUsuario;

	@Autowired
	private BuscaDadosDeUsuarios buscaDadosDeUsuarios;

	@PostMapping
	@Operation(summary = "Criar usuário", description = "Cria um usuário no sistema")
	public ResponseEntity<Usuario> criarUsuario(@RequestBody @Valid DadosCriarUsuario dados) {
		Usuario usuario = criaUsuario.criar(dados);
		URI uri = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/id={id}")
				.buildAndExpand(usuario.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	@Operation(summary = "Remover usuário", description = "Remove algum usuário existente")
	public ResponseEntity<Void> removerUsuario(@PathVariable Long id) {
		removeUsuario.remover(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	@Operation(summary = "Editar usuário", description = "Edita algum usuário existente")
	public ResponseEntity<DadosDeUsuario> editarUsuario(@RequestBody @Valid DadosEditarUsuario dados) {
		return ResponseEntity.ok().body(editaUsuario.editar(dados));
	}

	@PutMapping(value = "/alterar-senha")
	@Operation(summary = "Alterar senha de usuário", description = "Atera a senha de algum usuário existente")
	public ResponseEntity<Void> alterarSenhaDeUsuario(@RequestBody @Valid DadosAlterarSenhaDeUsuario dados) {
		alteraSenhaDeUsuario.alterar(dados);
		return ResponseEntity.ok().build();
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar usuário", description = "Busca algum usuário existente pelo id")
	public ResponseEntity<DadosDeUsuario> buscarUsuario(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeUsuarios.buscarPeloId(id));
	}

	@GetMapping
	@Operation(summary = "Buscar todos usuários", description = "Busca todos usuário existentes")
	public ResponseEntity<List<DadosDeUsuario>> buscarTodosUsuarios() {
		return ResponseEntity.ok().body(buscaDadosDeUsuarios.buscaTodos());
	}
}
