package br.com.totemAutoatendimento.aplicacao.usuario.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeUsuario {

	private Long id;
	
	private String username;
	
	private List<TipoPerfil> perfis = new ArrayList<>();
	
	public DadosDeUsuario(Usuario usuario) {
		this.id = usuario.getId();
		this.username = usuario.getUsername();
		usuario.getPerfis().forEach(perfil -> this.perfis.add(perfil.getTipo()));
	}
	
}
