package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.cliente;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.EmailEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.EnderecoEntity;
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
@Entity
@Table(name = "clientes")
public class ClienteEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(unique = true)
	private String cpf;

	private String telefone;

	@Embedded
	private EmailEntity email;

	@OneToOne(cascade = CascadeType.ALL)
	private EnderecoEntity endereco;
	
	public ClienteEntity(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = new EmailEntity(cliente.getEmail().getEndereco());
		this.endereco = new EnderecoEntity(cliente.getEndereco());
	}

	public Cliente converterParaCliente() {
		return new Cliente(this.id, this.nome, this.cpf, this.telefone, new Email(this.email.getEndereco()),
				this.endereco.converterParaEndereco());
	}

}
