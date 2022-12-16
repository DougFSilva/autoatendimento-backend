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

@Service
public class AutenticacaoDeUsuario {

    @Autowired
    private AuthenticationManager authenticationManager;

    public Usuario executar(DadosDeLogin dados) {
        UsernamePasswordAuthenticationToken autenticacaoToken = new UsernamePasswordAuthenticationToken(
                dados.registro(), dados.password());
        try {
            Authentication autenticacao = authenticationManager.authenticate(autenticacaoToken); // Tenta fazer a autenticação
            SecurityContextHolder.getContext().setAuthentication(autenticacao);
            return (Usuario) autenticacao.getPrincipal();
        } catch (AuthenticationException e) {
            throw new ErroNaAutenticacaoDeUsuario("Erro na autenticação do usuário. Usuário e/ou senha inválidos!");
        }

    }
}
