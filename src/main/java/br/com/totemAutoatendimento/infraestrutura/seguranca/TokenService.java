package br.com.totemAutoatendimento.infraestrutura.seguranca;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value("${jwt.expiracao}")
    private long expiracao;

    @Value("${jwt.chaveSecreta}")
    private String chaveSecreta;

    public String gerarToken(Authentication authentication) {
        Usuario usuario = (Usuario) authentication.getPrincipal();// Busca o usuário autenticado
        return Jwts.builder().setIssuer("API de Totem de autoatendimento")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiracao))
                .signWith(SignatureAlgorithm.HS512, chaveSecreta.getBytes()).compact();
    }

    public boolean verificarValidadeDoToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.chaveSecreta.getBytes()).parseClaimsJws(token);// Verifica se o token é
                                                                                            // válido
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public Long buscarIdDoUsuario(String token) {
        // Cria um objeto com os dados dentro do token
        Claims claims = Jwts.parser().setSigningKey(this.chaveSecreta.getBytes()).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject()); // Retorna o subject (este que foi declarado no metodo geraToken,
                                                    // nesse cado o id do usuario)
    }
}
