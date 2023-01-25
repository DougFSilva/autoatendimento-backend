package br.com.totemAutoatendimento.infraestrutura.config;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.MercadoriaEntity;
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
public class Teste {

	private MercadoriaEntity mercadoria;
	
	private Double quantidade;
}
