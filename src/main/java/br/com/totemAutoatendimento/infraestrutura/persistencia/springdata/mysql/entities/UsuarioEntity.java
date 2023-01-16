package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class UsuarioEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	@Column(unique = true)
	private String cpf;

	@Column(unique = true)
	private String registro;

	@Embedded
	private EmailEntity email;

	@Embedded
	@Column(name = "password")
	private PasswordEntity password;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<PerfilEntity> perfis = new ArrayList<>();

}
