package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "subcatergorias")
public class SubcategoriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String nome;

    public SubcategoriaEntity(Subcategoria subcategoria){
        this.id = subcategoria.getId();
        this.nome = subcategoria.getNome();
    }

    public Subcategoria converterParaSubcategoria(){
        return new Subcategoria(this.id, this.nome);
    }

}
