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
import br.com.totemAutoatendimento.aplicacao.cartao.CriaCartao;
import br.com.totemAutoatendimento.aplicacao.cartao.RemoveCartao;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/cartao")
@SecurityRequirement(name = "api-security")
public class CartaoController {

	@Autowired
	private CriaCartao criaCartao;
	
	@Autowired
	private RemoveCartao removeCartao;
	
	@Autowired
	private BuscaTodosCartoes buscaTodosCartoes;
	
	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;
	
	@PostMapping("/{codigo}")
	public ResponseEntity<Cartao> criarCartao(@PathVariable String codigo) {
		Cartao cartao = criaCartao.criar(codigo, usuarioAutenticado());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{codigo}").buildAndExpand(cartao.getCodigo()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@DeleteMapping("/{codigo}")
	public ResponseEntity<Void> removerCartao(@PathVariable String codigo) {
		removeCartao.remover(codigo, usuarioAutenticado());
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<Cartao>> buscarTodosCartoes() {
		List<Cartao> cartoes = buscaTodosCartoes.buscar(usuarioAutenticado());
		return ResponseEntity.ok().body(cartoes);
	}
	
	private Usuario usuarioAutenticado() {
		return autenticacaoDeUsuario.recuperarAutenticado();
	}
}
