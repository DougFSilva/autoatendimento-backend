package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "codigo")
@Entity
@Table(name = "cartoes")
@ToString
public class CartaoEntity {

	@Id
	private String codigo;
}
