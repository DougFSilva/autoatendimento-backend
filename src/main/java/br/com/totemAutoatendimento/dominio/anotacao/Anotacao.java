package br.com.totemAutoatendimento.dominio.anotacao;

import java.time.LocalDateTime;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Anotacao {

	private Long id;
	
	private LocalDateTime timestamp;
	
	private Usuario registrador;
	
	private String descricao;
	
	private NivelDeImportancia nivelDeImportancia;
	
}
