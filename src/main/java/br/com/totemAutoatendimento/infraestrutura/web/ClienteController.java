package br.com.totemAutoatendimento.infraestrutura.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.BuscarCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.CriarCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.DadosCriarCliente;
import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.RemoverCliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

@RestController
@RequestMapping(value = "/cliente")
public class ClienteController {
	
	@Autowired
	private ClienteRepository repository;
	
	@PostMapping
	public ResponseEntity<Cliente> criarCliente(@RequestBody @Valid DadosCriarCliente dados){
		CriarCliente criarCliente = new CriarCliente(repository);
		Cliente cliente = criarCliente.executar(dados);
		System.out.println(cliente);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cliente.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> buscarCliente(@PathVariable Long id){
		BuscarCliente buscarCliente = new BuscarCliente(repository);
		return ResponseEntity.ok().body(buscarCliente.executar(id));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> removerCliente(@PathVariable Long id){
		RemoverCliente removerCliente = new RemoverCliente(repository);
		removerCliente.executar(id);
		return ResponseEntity.noContent().build();
	}
}
