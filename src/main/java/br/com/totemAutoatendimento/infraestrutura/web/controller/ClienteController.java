package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;

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

import br.com.totemAutoatendimento.aplicacao.cliente.BuscaDadosDeClientes;
import br.com.totemAutoatendimento.aplicacao.cliente.CadastraCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.EditaCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.DeletaCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosCriarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosEditarCliente;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/cliente")
@SecurityRequirement(name = "api-security")
public class ClienteController {

	@Autowired
	private CadastraCliente cadastraCliente;

	@Autowired
	private DeletaCliente deletaCliente;

	@Autowired
	private EditaCliente editaCliente;

	@Autowired
	private BuscaDadosDeClientes buscaDadosDeClientes;
	
	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;
	
	@PostMapping
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade"}, allEntries = true)
	@Operation(summary = "Cadastrar cliente", description = "Cadastra um novo cliente no sistema")
	public ResponseEntity<Cliente> cadastrarCliente(@RequestBody @Valid DadosCriarCliente dados) {
		Cliente cliente = cadastraCliente.cadastrar(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/{id}")
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade"}, allEntries = true)
	@Operation(summary = "Deletar cliente", description = "Deleta um cliente existente no sistema")
	public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
		deletaCliente.deletar(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}")
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade" })
	@Operation(summary = "Editar cliente", description = "Edita um cliente existente no sistema")
	public ResponseEntity<DadosDeCliente> editarCliente(@PathVariable Long id, @RequestBody @Valid DadosEditarCliente dados) {
		return ResponseEntity.ok().body(editaCliente.editar(id, dados, usuarioAutenticado()));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar cliente", description = "Busca um cliente existente no sistema pelo id")
	public ResponseEntity<DadosDeCliente> buscarCliente(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarPeloId(id, usuarioAutenticado()));
	}

	@GetMapping("/cpf/{cpf}")
	@Operation(summary = "Buscar cliente por cpf", description = "Busca um cliente existente no sistema por cpf")
	public ResponseEntity<DadosDeCliente> buscarClientePorCpf(@PathVariable String cpf) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarPeloCpf(cpf, usuarioAutenticado()));
	}

	@GetMapping("/cidade/{cidade}")
	@Cacheable("buscarClientesPorCidade")
	@Operation(summary = "Buscar clientes por cidade", description = "Busca clientes pela cidade")
	public ResponseEntity<Page<DadosDeCliente>> buscarClientesPorCidade(Pageable paginacao,
			@PathVariable String cidade) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarPelaCidade(paginacao, cidade, usuarioAutenticado()));
	}

	@GetMapping
	@Cacheable("buscarTodosClientes")
	@Operation(summary = "Buscar todos clientes", description = "Busca todos os clientes existentes no sistema")
	public ResponseEntity<Page<DadosDeCliente>> buscarTodosClientes(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarTodos(paginacao, usuarioAutenticado()));
	}
	
	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}
	
}
