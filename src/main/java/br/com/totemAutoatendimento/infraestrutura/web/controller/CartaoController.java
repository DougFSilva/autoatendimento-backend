package br.com.totemAutoatendimento.infraestrutura.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.totemAutoatendimento.aplicacao.cartao.BuscaTodosCartoes;
import br.com.totemAutoatendimento.aplicacao.cartao.CadastraCartao;
import br.com.totemAutoatendimento.aplicacao.cartao.DeletaCartao;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/cartao")
@SecurityRequirement(name = "api-security")
public class CartaoController {

	@Autowired
	private CadastraCartao cadastraCartao;
	
	@Autowired
	private DeletaCartao deletaCartao;
	
	@Autowired
	private BuscaTodosCartoes buscaTodosCartoes;
	
	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;
	
	@PostMapping("/{codigo}")
	@Operation(summary = "Cadastrar cartão", description = "Cadastra um novo cartão no sistema")
	public ResponseEntity<Cartao> cadastrarCartao(@PathVariable String codigo) {
		Cartao cartao = cadastraCartao.cadastrar(codigo, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(cartao.getCodigo()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{codigo}")
	@Operation(summary = "Deletar cartão", description = "Deleta um cartão cadastrado no sistema")
	public ResponseEntity<Void> deletarCartao(@PathVariable String codigo) {
		deletaCartao.deletar(codigo, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	@Operation(summary = "Buscar todos os cartões", description = "Busca todos cartões castrados no sistema")
	public ResponseEntity<List<Cartao>> buscarTodosCartoes() {
		List<Cartao> cartoes = buscaTodosCartoes.buscar(usuarioAutenticado());
		return ResponseEntity.ok().body(cartoes);
	}
	
	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}
}
