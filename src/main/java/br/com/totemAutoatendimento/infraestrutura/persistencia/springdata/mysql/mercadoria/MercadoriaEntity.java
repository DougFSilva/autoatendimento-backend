package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

    @Column(unique = true)
    private String codigo;

    @ManyToOne(cascade = CascadeType.MERGE)
    private CategoriaEntity categoria;

    @ManyToOne(cascade = CascadeType.MERGE)
    private SubcategoriaEntity subcategoria;

    private String descricao;

    private BigDecimal preco;

    private Boolean promocao;

    private BigDecimal precoPromocional;

    private String imagem;

    private Boolean disponivel;

    public MercadoriaEntity(Mercadoria mercadoria) {
        this.id = mercadoria.getId();
        this.codigo = mercadoria.getCodigo();
        this.categoria = new CategoriaEntity(mercadoria.getCategoria());
        this.subcategoria = new SubcategoriaEntity(mercadoria.getSubcategoria());
        this.descricao = mercadoria.getDescricao();
        this.preco = mercadoria.getPreco();
        this.promocao = mercadoria.getPromocao();
        this.precoPromocional = mercadoria.getPrecoPromocional();
        this.imagem = mercadoria.getImagem();
        this.disponivel = mercadoria.getDisponivel();
    }

    public Mercadoria converterParaMercadoria() {
        Categoria categorgia = this.categoria.converterParaCategoria();
        Subcategoria subcategoria = this.subcategoria.converterParaSubcategoria();
        return new Mercadoria(id, codigo, categorgia, subcategoria, descricao,preco,
                promocao, precoPromocional, imagem, disponivel);
    }
}
