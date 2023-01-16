package br.com.totemAutoatendimento.dominio.mercadoria;

import java.math.BigDecimal;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = { "id", "codigo" })
@ToString
public class Mercadoria {

	private Long id;

	private String codigo;

	private Subcategoria subcategoria;

	private String descricao;

	private BigDecimal preco;

	private Boolean promocao;

	private BigDecimal precoPromocional;
	
	private Boolean disponivel;

	private String imagem;


}
