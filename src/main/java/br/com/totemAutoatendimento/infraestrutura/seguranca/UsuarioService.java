package br.com.totemAutoatendimento.infraestrutura.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.UsuarioEntityDao;

@Service
public class UsuarioService implements UserDetailsService {

	@Autowired
	private UsuarioEntityDao repository;

	@Override	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByRegistro(username).get();
	}
	

}
