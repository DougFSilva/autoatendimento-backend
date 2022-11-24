package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class EmailEntity {

	private String endereco;
}
