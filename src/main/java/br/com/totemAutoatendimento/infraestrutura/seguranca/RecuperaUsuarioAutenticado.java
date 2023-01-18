package br.com.totemAutoatendimento.infraestrutura.seguranca;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;

@Service
public class RecuperaUsuarioAutenticado {

	public Usuario recuperar() {
		return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
