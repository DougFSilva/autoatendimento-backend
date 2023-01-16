package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "mercadorias")
public class MercadoriaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String codigo;

	@ManyToOne(cascade = CascadeType.MERGE)
	private SubcategoriaEntity subcategoria;

	private String descricao;

	private BigDecimal preco;

	private Boolean promocao;

	private BigDecimal precoPromocional;
	
	private Boolean disponivel;

	private String imagem;

	

}
