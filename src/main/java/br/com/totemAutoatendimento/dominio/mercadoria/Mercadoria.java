package br.com.totemAutoatendimento.dominio.mercadoria;

import java.math.BigDecimal;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
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
public class Mercadoria {

	private Long id;

	private String codigo;

	private Categoria categoria;

	private Subcategoria subcategoria;

	private String descricao;

	private BigDecimal preco;

	private Boolean promocao;

	private BigDecimal precoPromocional;

	private String imagem;

	private Boolean disponivel;

}
