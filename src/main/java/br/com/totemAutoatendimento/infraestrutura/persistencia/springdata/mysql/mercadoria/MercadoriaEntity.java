package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria.CategoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria.SubcategoriaEntity;
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
@Entity
@Table(name = "mercadorias")
public class MercadoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private CategoriaEntity categoria;

    @ManyToOne(cascade = CascadeType.ALL)
    private SubcategoriaEntity subcategoria;

    private String descricao;

    private Integer quantidade;

    private BigDecimal preco;

    private Boolean promocao;

    private BigDecimal precoPromocional;

    private String imagem;

    public MercadoriaEntity(Mercadoria mercadoria) {
        this.id = mercadoria.getId();
        this.categoria = new CategoriaEntity(mercadoria.getCategoria());
        this.subcategoria = new SubcategoriaEntity(mercadoria.getSubcategoria());
        this.descricao = mercadoria.getDescricao();
        this.quantidade = mercadoria.getQuantidade();
        this.preco = mercadoria.getPreco();
        this.promocao = mercadoria.getPromocao();
        this.precoPromocional = mercadoria.getPrecoPromocional();
        this.imagem = mercadoria.getImagem();
    }

    public Mercadoria converterParaMercadoria() {
        Categoria categorgia = this.categoria.converterParaCategoria();
        Subcategoria subcategoria = this.subcategoria.converterParaSubcategoria();
        return new Mercadoria(this.id, categorgia, subcategoria, this.descricao, this.quantidade, this.preco,
                this.promocao, this.precoPromocional, this.imagem);
    }
}
