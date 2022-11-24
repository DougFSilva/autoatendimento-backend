package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente;

import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda.ComandaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.EmailEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.EnderecoEntity;
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
public class ClienteEntity {

	private Long id;

	private String nome;

	private Integer cpf;

	private Integer telefone;
	
	private EmailEntity email;

	private EnderecoEntity endereco;

	private List<ComandaEntity> comandas = new ArrayList<>();
}
