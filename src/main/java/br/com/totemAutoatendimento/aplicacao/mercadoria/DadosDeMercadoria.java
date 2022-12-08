package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.math.BigDecimal;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeMercadoria {

    private Long id;

    private String codigo;
	
	private String categoria;
	
	private String subcategoria;
	
	private String descricao;
	
	private Integer quantidade;
	
	private BigDecimal preco;
	
	private Boolean promocao;
	
	private BigDecimal precoPromocional;

    private String imagem;
    
    public DadosDeMercadoria(Mercadoria mercadoria){
        this.id = mercadoria.getId();
        this.codigo = mercadoria.getCodigo();
        this.categoria = mercadoria.getCategoria().getNome();
        this.subcategoria = mercadoria.getSubcategoria().getNome();
        this.descricao = mercadoria.getDescricao();
        this.quantidade = mercadoria.getQuantidade();
        this.preco = mercadoria.getPreco();
        this.promocao = mercadoria.getPromocao();
        this.precoPromocional = mercadoria.getPrecoPromocional();
        this.imagem = mercadoria.getImagem();
    }
}
