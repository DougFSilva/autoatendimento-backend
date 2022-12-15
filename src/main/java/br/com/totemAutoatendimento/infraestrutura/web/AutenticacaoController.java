package br.com.totemAutoatendimento.infraestrutura.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.totemAutoatendimento.aplicacao.usuario.DadosDeUsuario;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.AutenticacaoDeUsuario;
import br.com.totemAutoatendimento.infraestrutura.seguranca.DadosDeLogin;
import br.com.totemAutoatendimento.infraestrutura.seguranca.TokenService;

@RestController
@RequestMapping(value = "/autenticacao")
public class AutenticacaoController {

	@Autowired
	private AuthenticationManager authenticationManager;
    
	@Autowired
	private TokenService tokenService;

	@PostMapping
	public ResponseEntity<DadosDeUsuario> autenticar(@RequestBody @Valid DadosDeLogin dados) {
		AutenticacaoDeUsuario autenticacaoDeUsuario = new AutenticacaoDeUsuario(authenticationManager);
		Authentication autenticacao = autenticacaoDeUsuario.executar(dados);
		Usuario usuario = (Usuario)autenticacao.getPrincipal(); // Busca o usuário
		String token = tokenService.gerarToken(autenticacao); // Gera o token
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setBearerAuth(token);
		return ResponseEntity.ok().headers(httpHeaders).body(new DadosDeUsuario(usuario));

	}
}
