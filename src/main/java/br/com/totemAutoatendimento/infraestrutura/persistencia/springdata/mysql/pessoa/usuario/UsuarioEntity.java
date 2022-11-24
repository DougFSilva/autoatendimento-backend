package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.pessoa.Email;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Password;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.EmailEntity;
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

	@OneToMany(cascade = CascadeType.ALL)
	private List<PerfilEntity> perfis = new ArrayList<>();
	
	public UsuarioEntity(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.cpf = usuario.getCpf();
		this.registro = usuario.getRegistro();
		this.email = new EmailEntity(usuario.getEmail());
		this.password = new PasswordEntity(usuario.getPassword());
		this.perfis = usuario.getPerfis().stream().map(PerfilEntity::new).toList();
		
	}
	
	public Usuario converterParaUsuario() {
		Email email = new Email(this.email.getEndereco());
		Password password = new Password(this.password.getSenha());
		List<Perfil> perfis = this.perfis.stream().map(perfil -> new Perfil(perfil.getTipo())).toList();
		return new Usuario(this.id, this.nome, this.cpf, this.registro, email, password, perfis);
	}

}
