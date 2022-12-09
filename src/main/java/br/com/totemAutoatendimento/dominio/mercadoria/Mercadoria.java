package br.com.totemAutoatendimento.dominio.mercadoria;

import java.math.BigDecimal;

import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
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
	
	private Integer quantidade;
	
	private BigDecimal preco;
	
	private Boolean promocao;
	
	private BigDecimal precoPromocional;

	private String imagem;

	public void adicionarQuantidade(Integer quantidade){
		this.quantidade += quantidade;
	}

	public void removerQuantidade(Integer quantidade){
		if(quantidade <= 0){
			throw new ViolacaoDeIntegridadeDeDadosException("Não é permitido remover um valor negativo ou zero!");
		}
		if(quantidade > this.quantidade){
			throw new ViolacaoDeIntegridadeDeDadosException("Não é possível remover " + quantidade + " mercadoria(s), pois há somente " + this.quantidade + " !");
		}
		this.quantidade -= quantidade;
	}

}
