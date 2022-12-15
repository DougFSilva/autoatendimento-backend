package br.com.totemAutoatendimento.infraestrutura.seguranca;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import br.com.totemAutoatendimento.dominio.exception.ErroNaAutenticacaoDeUsuario;

public class AutenticacaoDeUsuario {

    private AuthenticationManager authenticationManager;

    public AutenticacaoDeUsuario(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication executar(DadosDeLogin dados) {
        UsernamePasswordAuthenticationToken autenticacaoToken = new UsernamePasswordAuthenticationToken(
                dados.registro(), dados.password());
        try {
            return authenticationManager.authenticate(autenticacaoToken); // Tenta fazer a autenticação
        } catch (AuthenticationException e) {
            throw new ErroNaAutenticacaoDeUsuario("Erro na autenticação do usuário. Usuário e/ou senha inválidos!");
        }

    }
}
