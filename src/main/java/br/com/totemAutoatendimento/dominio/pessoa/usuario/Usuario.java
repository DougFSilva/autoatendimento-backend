package br.com.totemAutoatendimento.dominio.pessoa.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

	private Long id;
	
	private String nome;
	
	private Integer cpf;
	
	private Integer registro;
	
	private Email email;
	
	private Password password;
	
	private List<Perfil> perfis = new ArrayList<>();
}
