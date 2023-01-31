package br.com.totemAutoatendimento.aplicacao.funcionario.dto;

import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeFuncionario {

	protected Long id;

	protected String matricula;

	private String nome;

	private String cpf;
	
	private String email;
	
	private String username;
	
	private List<String> perfis = new ArrayList<>();
	
	public DadosDeFuncionario(Funcionario funcionario) {
		this.id = funcionario.getId();
		this.matricula = funcionario.getMatricula();
		this.nome = funcionario.getNome();
		this.cpf = funcionario.getCpf();
		this.email = funcionario.getEmail().getEndereco();
		this.username = funcionario.getUsuario().getUsername();
		funcionario.getUsuario().getPerfis().forEach(perfil -> this.perfis.add(perfil.getTipo().getDescricao()));
	}
}
