package br.com.totemAutoatendimento.dominio.mercadoria;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Mercadoria {

	private Long id;
	
	private Categoria categoria;
	
	private Subcategoria subcategoria;
	
	private String descricao;
	
	private Integer quantidade;
	
	private BigDecimal preco;
	
	private Boolean promocao;
	
	private BigDecimal precoPromocional;
	
}
