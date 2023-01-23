package anotacao.copy;

import java.time.LocalDateTime;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class Anotacao {

	private Long id;
	
	private LocalDateTime timestamp;
	
	private Usuario registrador;
	
	private String descricao;
}
