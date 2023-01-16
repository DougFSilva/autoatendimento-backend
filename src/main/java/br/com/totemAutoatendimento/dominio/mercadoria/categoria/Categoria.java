package br.com.totemAutoatendimento.dominio.mercadoria.categoria;

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
@EqualsAndHashCode(of = {"id", "nome"})
@ToString
public class Categoria {

	private Long id;

	private String nome;

	private String imagem;

	public Categoria(String nome, String imagem) {
		this.nome = nome;
		this.imagem = imagem;
	}

}
