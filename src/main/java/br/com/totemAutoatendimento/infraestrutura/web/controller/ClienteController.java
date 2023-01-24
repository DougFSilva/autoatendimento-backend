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
import br.com.totemAutoatendimento.aplicacao.cliente.CriaCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.EditaCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.RemoveCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosCriarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosEditarCliente;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

	@Autowired
	private CriaCliente criaCliente;

	@Autowired
	private RemoveCliente removeCliente;

	@Autowired
	private EditaCliente editaCliente;

	@Autowired
	private BuscaDadosDeClientes buscaDadosDeClientes;
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	@PostMapping
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade"}, allEntries = true)
	@Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
	public ResponseEntity<Cliente> criarCliente(@RequestBody @Valid DadosCriarCliente dados) {
		Cliente cliente = criaCliente.criar(dados, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade"}, allEntries = true)
	@Operation(summary = "Remover cliente", description = "Remove algum cliente existente")
	public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
		removeCliente.remover(id, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade" })
	@Operation(summary = "Editar cliente", description = "Edita algum cliente existente")
	public ResponseEntity<DadosDeCliente> editarCliente(@PathVariable Long id, @RequestBody @Valid DadosEditarCliente dados) {
		return ResponseEntity.ok().body(editaCliente.editar(id, dados, usuarioAutenticado()));
	}

	@GetMapping(value = "/{id}")
	@Operation(summary = "Buscar cliente", description = "Busca algum cliente existente pelo id")
	public ResponseEntity<DadosDeCliente> buscarCliente(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarPeloId(id, usuarioAutenticado()));
	}

	@GetMapping(value = "/cpf/{cpf}")
	@Operation(summary = "Buscar cliente por cpf", description = "Busca algum cliente existente por cpf")
	public ResponseEntity<DadosDeCliente> buscarClientePorCpf(@PathVariable String cpf) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarPeloCpf(cpf, usuarioAutenticado()));
	}

	@GetMapping(value = "/cidade/{cidade}")
	@Cacheable(value = "buscarClientesPorCidade")
	@Operation(summary = "Buscar clientes por cidade", description = "Busca clientes pela cidade")
	public ResponseEntity<Page<DadosDeCliente>> buscarClientesPorCidade(Pageable paginacao,
			@PathVariable String cidade) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarPelaCidade(paginacao, cidade, usuarioAutenticado()));
	}

	@GetMapping
	@Cacheable(value = "buscarTodosClientes")
	@Operation(summary = "Buscar todos clientes", description = "Busca todos os clientes existentes")
	public ResponseEntity<Page<DadosDeCliente>> buscarTodosClientes(Pageable paginacao) {
		return ResponseEntity.ok().body(buscaDadosDeClientes.buscarTodos(paginacao, usuarioAutenticado()));
	}
	
	private Usuario usuarioAutenticado() {
		return autenticacaoService.recuperarAutenticado();
	}
	
}
