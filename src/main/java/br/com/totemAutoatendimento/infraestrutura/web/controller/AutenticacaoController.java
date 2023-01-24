package br.com.totemAutoatendimento.infraestrutura.web.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosDeUsuario;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.DadosDeLogin;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class AutenticacaoController {

	@Autowired
	private AutenticacaoDeUsuario autenticacaoDeUsuario;

	@PostMapping("/login")
	@Operation(summary = "Auntenticar no sistema", description = "Faz a autenticação no sistema com usuário e senha")
	public ResponseEntity<DadosDeUsuario> login(@RequestBody @Valid DadosDeLogin dados) {
		Usuario usuario = autenticacaoDeUsuario.autenticar(dados);

		return ResponseEntity.ok().body(new DadosDeUsuario(usuario));
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(HttpSession session) {
		session.invalidate();
		return ResponseEntity.noContent().build();
	}
}
