package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "categorias")
public class CategoriaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String nome;

	private String imagem;
	
	public CategoriaEntity(Categoria categoria) {
		this.id = categoria.getId();
		this.nome = categoria.getNome();
		this.imagem = categoria.getImagem();
	}
	
	public Categoria converterParaCategoria() {
		return new Categoria(this.id, this.nome, this.imagem);
	}
}
