package br.com.totemAutoatendimento.dominio.pessoa;

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
public class Endereco {

	private Long id;
	
	private Estado estado;
	
	private String cidade;
	
	private String bairro;
	
	private String rua;
	
	private String numero;
}
