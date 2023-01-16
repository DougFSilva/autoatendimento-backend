package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeSubcategoria {

	private Long id;
	
	private String categoria;
	
	private String nome;
	
	private String imagem;
	
	public DadosDeSubcategoria(Subcategoria subcategoria) {
		this.id = subcategoria.getId();
		this.categoria = subcategoria.getCategoria().getNome();
		this.nome = subcategoria.getNome();
		this.imagem = subcategoria.getImagem();
	}
}
