package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "perfis")
public class PerfilEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private TipoPerfil tipo;
	
	public PerfilEntity(Perfil perfil) {
		this.tipo = perfil.getTipo();
	}
}
