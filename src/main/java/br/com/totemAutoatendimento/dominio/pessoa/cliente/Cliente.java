package br.com.totemAutoatendimento.dominio.pessoa.cliente;

import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.Endereco;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Cliente {

	private Long id;
	
	private String nome;
	
	private Integer cpf;
	
	private Integer telefone;
	
	private Email email;
	
	private Endereco endereco;
	
	private List<Comanda> comandas = new ArrayList<>();
	
	public Cliente(String nome, Integer cpf, Integer telefone, Email email, Endereco endereco) {
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
		this.email = email;
		this.endereco = endereco;
	}
	
	public void adicionarComanda(Comanda comanda) {
		this.comandas.add(comanda);
	}
	
}
