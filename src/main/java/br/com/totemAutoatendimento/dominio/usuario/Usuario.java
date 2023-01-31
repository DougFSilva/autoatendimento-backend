package br.com.totemAutoatendimento.dominio.usuario;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "username"})
@ToString
public class Usuario {

	private Long id;

	private String username;

	private Password password;

	private List<Perfil> perfis = new ArrayList<>();
}
