package br.com.totemAutoatendimento.dominio.usuario;

import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.Email;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id", "cpf", "registro" })
@Getter
@Setter
@ToString
public class Usuario {

	private Long id;

	private String nome;

	private String cpf;

	private String registro;

	private Email email;

	private Password password;

	private List<Perfil> perfis = new ArrayList<>();

}
