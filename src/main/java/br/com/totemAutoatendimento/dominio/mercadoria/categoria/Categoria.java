package br.com.totemAutoatendimento.dominio.mercadoria.categoria;

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
public class Categoria {

	private Long id;
	
	private String nome;

	private String imagem;
}
