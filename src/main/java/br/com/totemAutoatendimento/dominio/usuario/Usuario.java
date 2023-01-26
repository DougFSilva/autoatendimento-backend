package br.com.totemAutoatendimento.dominio.usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.totemAutoatendimento.dominio.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "cpf", "registro"})
@Setter
@ToString
public class Usuario implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String nome;

	private String cpf;

	private String registro;

	private Email email;

	private Password password;

	private List<Perfil> perfis = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRegistro() {
		return registro;
	}

	public Email getEmail() {
		return email;
	}

	@Override
	public String getPassword() {
		return password.getSenha();
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return perfis;
	}

	@Override
	public String getUsername() {
		return registro;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
