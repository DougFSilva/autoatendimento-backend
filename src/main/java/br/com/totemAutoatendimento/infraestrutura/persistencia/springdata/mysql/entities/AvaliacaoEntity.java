package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.avaliacao.NotaAvaliacao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "avaliacoes")
public class AvaliacaoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private LocalDateTime timestamp;
	
	@ManyToOne
	private TotemEntity totem;
	
	@Enumerated(EnumType.STRING)
	private NotaAvaliacao QualidadeDeMercadorias;
	
	@Enumerated(EnumType.STRING)
	private NotaAvaliacao OpcoesDeMercadorias;
	
	@Enumerated(EnumType.STRING)
	private NotaAvaliacao tempoDeAtendimento;
	
	@Enumerated(EnumType.STRING)
	private NotaAvaliacao ambiente;
	
	@Enumerated(EnumType.STRING)
	private NotaAvaliacao experienciaComAutoatendimento;
	
	@Enumerated(EnumType.STRING)
	private NotaAvaliacao funcionarios;
	
	@Enumerated(EnumType.STRING)
	private NotaAvaliacao gerencia;
}
