package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.BuscarClientesPorCidade;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.BuscarDadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.BuscarTodosClientes;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.CriarCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.DadosCriarCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.DadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.DadosEditarCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.EditarCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.RemoverCliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;

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
	private BuscarClientesPorCidade buscarClientesPorCidade;

	@Autowired
	private BuscarTodosClientes buscarTodosClientes;
	

	@PostMapping
	@Transactional
	public ResponseEntity<Cliente> criarCliente(@RequestBody @Valid DadosCriarCliente dados) {
		Cliente cliente = criarCliente.executar(dados);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId())
				.toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removerCliente(@PathVariable Long id) {
		removerCliente.executar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping
	@Transactional
	public ResponseEntity<DadosDeCliente> editarCliente(@RequestBody @Valid DadosEditarCliente dados) {
		return ResponseEntity.ok().body(editarCliente.executar(dados));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DadosDeCliente> buscarCliente(@PathVariable Long id) {
		return ResponseEntity.ok().body(buscarDadosDeCliente.executar(id));
	}

	@GetMapping(value = "/cidade/{cidade}")
	public ResponseEntity<Page<DadosDeCliente>> buscarClientesPorCidade(Pageable paginacao,
			@PathVariable String cidade) {
		return ResponseEntity.ok().body(buscarClientesPorCidade.executar(paginacao, cidade));
	}

	@GetMapping
	public ResponseEntity<Page<DadosDeCliente>> buscarTodosClientes(Pageable paginacao) {
		return ResponseEntity.ok().body(buscarTodosClientes.executar(paginacao));
	}
}
