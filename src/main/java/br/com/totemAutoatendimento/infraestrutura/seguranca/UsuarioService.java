package br.com.totemAutoatendimento.infraestrutura.seguranca;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.exception.ErroNaAutenticacaoDeUsuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.UsuarioEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioEntityDao usuarioEntityDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UsuarioEntity> usuarioEntity = usuarioEntityDao.findByUsername(username);
		if(usuarioEntity.isPresent()) {
			return usuarioEntity.get();
		}
		throw new ErroNaAutenticacaoDeUsuario("Erro na autenticação do usuário. Usuário e/ou senha inválidos!");
	}

}
