package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
	
	@PostMapping
	@Transactional
	public ResponseEntity<Cliente> criarCliente(@RequestBody @Valid DadosCriarCliente dados){
		CriarCliente criarCliente = new CriarCliente(repository);
		Cliente cliente = criarCliente.executar(dados);
		System.out.println(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removerCliente(@PathVariable Long id){
		RemoverCliente removerCliente = new RemoverCliente(repository);
		removerCliente.executar(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity<DadosDeCliente> editarCliente (@RequestBody @Valid DadosEditarCliente dados){
		EditarCliente editarCliente = new EditarCliente(repository);
		return ResponseEntity.ok().body(editarCliente.executar(dados));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<DadosDeCliente> buscarCliente(@PathVariable Long id){
		BuscarDadosDeCliente buscarDadosDeCliente = new BuscarDadosDeCliente(repository);
		return ResponseEntity.ok().body(buscarDadosDeCliente.executar(id));
	}
	
	@GetMapping(value = "/cidade/{cidade}")
	public ResponseEntity<List<DadosDeCliente>> buscarClientesPorCidade(@PathVariable String cidade){
		BuscarClientesPorCidade buscarClientesPorCidade = new BuscarClientesPorCidade(repository);
		return ResponseEntity.ok().body(buscarClientesPorCidade.executar(cidade));
	}
	
	@GetMapping
	public ResponseEntity<List<DadosDeCliente>> buscarTodosClientes(){
		BuscarTodosClientes buscarTodosClientes = new BuscarTodosClientes(repository);
		return ResponseEntity.ok().body(buscarTodosClientes.executar());
	}
}
