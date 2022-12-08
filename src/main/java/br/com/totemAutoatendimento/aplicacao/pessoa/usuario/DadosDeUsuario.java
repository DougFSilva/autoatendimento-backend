package br.com.totemAutoatendimento.aplicacao.pessoa.usuario;

import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.pessoa.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeUsuario {

	private Long id;

	private String nome;

	private String cpf;

	private String registro;

	private String email;

	private List<Perfil> perfis = new ArrayList<>();
	
	public DadosDeUsuario(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.registro = usuario.getRegistro();
		this.email = usuario.getEmail().getEndereco();
		this.perfis = usuario.getPerfis();
	}
}
