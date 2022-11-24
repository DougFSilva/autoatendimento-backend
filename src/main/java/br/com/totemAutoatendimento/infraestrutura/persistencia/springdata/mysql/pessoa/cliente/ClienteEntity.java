package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.Endereco;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda.ComandaEntity;
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
@Table(name = "clientes")
@Entity
public class ClienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private Integer cpf;

	private Integer telefone;
	
	@Embedded
	private Email email;

	@OneToOne(cascade = CascadeType.ALL)
	private Endereco endereco;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ComandaEntity> comandas = new ArrayList<>();
}
