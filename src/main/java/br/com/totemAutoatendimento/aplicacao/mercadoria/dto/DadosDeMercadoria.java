package br.com.totemAutoatendimento.aplicacao.mercadoria.dto;

import java.math.BigDecimal;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeMercadoria {

    private Long id;

    private String codigo;
	
	private String subcategoria;
	
	private String descricao;
	
	private BigDecimal preco;
	
	private Boolean promocao;
	
	private BigDecimal precoPromocional;

    private String imagem;

    private Boolean disponivel;
    
    public DadosDeMercadoria(Mercadoria mercadoria){
        this.id = mercadoria.getId();
        this.codigo = mercadoria.getCodigo();
        this.subcategoria = mercadoria.getSubcategoria().getNome();
        this.descricao = mercadoria.getDescricao();
        this.preco = mercadoria.getPreco();
        this.promocao = mercadoria.getPromocao();
        this.precoPromocional = mercadoria.getPrecoPromocional();
        this.imagem = mercadoria.getImagem();
        this.disponivel = mercadoria.getDisponivel();
    }
}
