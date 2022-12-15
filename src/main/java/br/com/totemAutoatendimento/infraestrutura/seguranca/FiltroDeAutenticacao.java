package br.com.totemAutoatendimento.infraestrutura.seguranca;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class FiltroDeAutenticacao extends OncePerRequestFilter {

	private TokenService tokenService;
	private UsuarioRepository usuarioRepository;

	// A classe filter não aceitar Injeção de dependência com @AutoWired, por tanto
	// essa injeção é efetuada através do construtor
	public FiltroDeAutenticacao(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;	
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperarToken(request);
		boolean valido = tokenService.verificarValidadeDoToken(token);
		if (valido) {
			autenticar(token);
		}

		filterChain.doFilter(request, response);
	}

	private void autenticar(String token) {
		Long idDoUsuario = tokenService.buscarIdDoUsuario(token);
		Optional<Usuario> usuario = usuarioRepository.buscar(idDoUsuario);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario.get(),
				null, usuario.get().getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization"); // Busca por Authorization no hearder da requisição
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length()); // Retorna somente o token, excluindo "Bearer "
	}

}