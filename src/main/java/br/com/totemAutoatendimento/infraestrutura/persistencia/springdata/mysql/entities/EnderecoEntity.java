package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.dominio.cliente.Estado;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "enderecos")
public class EnderecoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private Estado estado;

	private String cidade;

	private String bairro;

	private String rua;

	private String numero;
	
	public EnderecoEntity(Endereco endereco) {
		this.id = endereco.getId();
		this.estado = endereco.getEstado();
		this.cidade = endereco.getCidade();
		this.bairro = endereco.getBairro();
		this.rua = endereco.getRua();
		this.numero = endereco.getNumero();
	}
	
	public Endereco converterParaEndereco() {
		return new Endereco(this.id, this.estado, this.cidade, this.bairro, this.rua, this.numero);
	}
}
