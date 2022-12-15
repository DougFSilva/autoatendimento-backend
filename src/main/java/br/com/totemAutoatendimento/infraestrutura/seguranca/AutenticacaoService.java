package br.com.totemAutoatendimento.infraestrutura.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.usuario.UsuarioEntityRepository;

@Service
public class AutenticacaoService implements UserDetailsService{

    @Autowired
    private UsuarioEntityRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.buscarPorRegistro(username).get();
    }
    
}
