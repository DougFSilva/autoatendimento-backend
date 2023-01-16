package br.com.totemAutoatendimento.dominio.mercadoria.subcategoria;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
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
@EqualsAndHashCode(of = { "id", "nome" })
@ToString
public class Subcategoria {

	private Long id;

	private Categoria categoria;

	private String nome;

	private String imagem;

}