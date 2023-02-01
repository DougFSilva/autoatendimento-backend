package br.com.totemAutoatendimento.aplicacao.Avaliacao.dto;

import javax.validation.constraints.NotNull;

import br.com.totemAutoatendimento.dominio.avaliacao.NotaAvaliacao;

public record DadosCriarAvaliacao(
		
		@NotNull
		NotaAvaliacao QualidadeDeMercadorias,
		
		@NotNull
		NotaAvaliacao OpcoesDeMercadorias,
		
		@NotNull
		NotaAvaliacao tempoDeAtendimento,
		
		@NotNull
		NotaAvaliacao ambiente,
		
		@NotNull
		NotaAvaliacao experienciaComAutoatendimento,
		
		@NotNull
		NotaAvaliacao funcionarios,
		
		@NotNull
		NotaAvaliacao gerencia
		
		) {

}
