package br.com.totemAutoatendimento.aplicacao.Avaliacao.dto;

import java.time.LocalDateTime;

import br.com.totemAutoatendimento.dominio.avaliacao.Avaliacao;
import br.com.totemAutoatendimento.dominio.avaliacao.NotaAvaliacao;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeAvaliacao {

	private Long id;

	private LocalDateTime timestamp;

	private String totem;

	private NotaAvaliacao QualidadeDeMercadorias;

	private NotaAvaliacao OpcoesDeMercadorias;

	private NotaAvaliacao tempoDeAtendimento;

	private NotaAvaliacao ambiente;

	private NotaAvaliacao experienciaComAutoatendimento;

	private NotaAvaliacao funcionarios;

	private NotaAvaliacao gerencia;
	
	public DadosDeAvaliacao(Avaliacao avaliacao) {
		this.id = avaliacao.getId();
		this.timestamp = avaliacao.getTimestamp();
		this.totem = avaliacao.getTotem().getIdentificador();
		this.QualidadeDeMercadorias = avaliacao.getQualidadeDeMercadorias();
		this.OpcoesDeMercadorias = avaliacao.getOpcoesDeMercadorias();
		this.tempoDeAtendimento = avaliacao.getTempoDeAtendimento();
		this.ambiente = avaliacao.getAmbiente();
		this.experienciaComAutoatendimento = avaliacao.getExperienciaComAutoatendimento();
		this.funcionarios = avaliacao.getFuncionarios();
		this.gerencia = avaliacao.getGerencia();
	}
}
