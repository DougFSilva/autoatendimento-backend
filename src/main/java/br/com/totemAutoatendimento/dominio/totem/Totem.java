package br.com.totemAutoatendimento.dominio.totem;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "identificador"})
@ToString
public class Totem {
	
	private Long id;

	private String identificador;

	private String localDeInstalacao;
	
	private Usuario usuario;
	
}
