package br.com.totemAutoatendimento.dominio.avaliacao;

import java.time.LocalDateTime;

import br.com.totemAutoatendimento.dominio.totem.Totem;
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
public class Avaliacao {
	
	public static final Integer tempoEntreAvaliacoes = 180;

	private Long id;
	
	private LocalDateTime timestamp;
	
	private Totem totem;
	
	private NotaAvaliacao QualidadeDeMercadorias;
	
	private NotaAvaliacao OpcoesDeMercadorias;
	
	private NotaAvaliacao tempoDeAtendimento;
	
	private NotaAvaliacao ambiente;
	
	private NotaAvaliacao experienciaComAutoatendimento;
	
	private NotaAvaliacao funcionarios;
	
	private NotaAvaliacao gerencia;
}
