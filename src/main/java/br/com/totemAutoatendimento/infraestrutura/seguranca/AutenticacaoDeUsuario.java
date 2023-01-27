package br.com.totemAutoatendimento.infraestrutura.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.exception.ErroNaAutenticacaoDeUsuario;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.UsuarioEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Service
public class AutenticacaoDeUsuario {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private LoggerAdapter logger;

	@Autowired
	private UsuarioEntityConverter usuarioEntityConverter;

	public Usuario autenticar(DadosDeLogin dados) {
		UsernamePasswordAuthenticationToken autenticacaoToken = new UsernamePasswordAuthenticationToken(
				dados.registro(), dados.password());
		try {
			Authentication autenticacao = authenticationManager.authenticate(autenticacaoToken);
			SecurityContextHolder.getContext().setAuthentication(autenticacao);
			return usuarioEntityConverter.converterParaUsuario((UsuarioEntity) autenticacao.getPrincipal());
		} catch (AuthenticationException e) {
			logger.error("Erro na autenticação do usuário. Usuário e/ou senha inválidos!");
			throw new ErroNaAutenticacaoDeUsuario("Erro na autenticação do usuário. Usuário e/ou senha inválidos!");
		}
	}

	public Usuario recuperarAutenticado() {
		return usuarioEntityConverter.converterParaUsuario((UsuarioEntity) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal());
	}
}
