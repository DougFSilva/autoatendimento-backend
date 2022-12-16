package br.com.totemAutoatendimento.infraestrutura.web;

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

import br.com.totemAutoatendimento.aplicacao.cliente.BuscarClientePorCpf;
import br.com.totemAutoatendimento.aplicacao.cliente.BuscarClientesPorCidade;
import br.com.totemAutoatendimento.aplicacao.cliente.BuscarDadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.BuscarTodosClientes;
import br.com.totemAutoatendimento.aplicacao.cliente.CriarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.DadosCriarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.DadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.DadosEditarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.EditarCliente;
import br.com.totemAutoatendimento.aplicacao.cliente.RemoverCliente;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {

	@Autowired
	private CriarCliente criarCliente;

	@Autowired
	private RemoverCliente removerCliente;

	@Autowired
	private EditarCliente editarCliente;

	@Autowired
	private BuscarDadosDeCliente buscarDadosDeCliente;

	@Autowired
	private BuscarClientePorCpf buscarClientePorCpf;

	@Autowired
	private BuscarClientesPorCidade buscarClientesPorCidade;

	@Autowired
	private BuscarTodosClientes buscarTodosClientes;

	@PostMapping
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade" })
	@Operation(summary = "Criar cliente", description = "Cria um novo cliente no sistema")
	public ResponseEntity<Cliente> criarCliente(@RequestBody @Valid DadosCriarCliente dados) {
		Cliente cliente = criarCliente.executar(dados);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade" })
		@Operation(summary = "Remover cliente", description = "Remove algum cliente existente")
	public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
		removerCliente.executar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	@CacheEvict(value = { "buscarTodosClientes", "buscarClientesPorCidade" })
		@Operation(summary = "Editar cliente", description = "Edita algum cliente existente")
	public ResponseEntity<DadosDeCliente> editarCliente(@RequestBody @Valid DadosEditarCliente dados) {
		return ResponseEntity.ok().body(editarCliente.executar(dados));
	}

	@GetMapping(value = "/{id}")
		@Operation(summary = "Buscar cliente", description = "Busca algum cliente existente pelo id")
	public ResponseEntity<DadosDeCliente> buscarCliente(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscarDadosDeCliente.executar(id));
	}

	@GetMapping(value = "/cpf/{cpf}")
		@Operation(summary = "Buscar cliente por cpf", description = "Busca algum cliente existente por cpf")
	public ResponseEntity<DadosDeCliente> buscarClientePorCpf(@PathVariable String cpf) {
		return ResponseEntity.ok().body(buscarClientePorCpf.executar(cpf));
	}

	@GetMapping(value = "/cidade/{cidade}")
	@Cacheable(value = "buscarClientesPorCidade")
		@Operation(summary = "Buscar clientes por cidade", description = "Busca clientes pela cidade")
	public ResponseEntity<Page<DadosDeCliente>> buscarClientesPorCidade(Pageable paginacao,
			@PathVariable String cidade) {
		return ResponseEntity.ok().body(buscarClientesPorCidade.executar(paginacao, cidade));
	}

	@GetMapping
	@Cacheable(value = "buscarTodosClientes")
		@Operation(summary = "Buscar todos clientes", description = "Busca todos os clientes existentes")
	public ResponseEntity<Page<DadosDeCliente>> buscarTodosClientes(Pageable paginacao) {
		return ResponseEntity.ok().body(buscarTodosClientes.executar(paginacao));
	}
}
