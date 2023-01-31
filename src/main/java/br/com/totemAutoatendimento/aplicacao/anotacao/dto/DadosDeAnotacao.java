package br.com.totemAutoatendimento.aplicacao.anotacao.dto;

import java.time.LocalDateTime;

import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.anotacao.NivelDeImportancia;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@ToString
public class DadosDeAnotacao {
	
	private Long id;

	private LocalDateTime timestamp;
	
	private String registrador;

	private String descricao;

	private NivelDeImportancia nivelDeImportancia;
	
	public DadosDeAnotacao(Anotacao anotacao) { 
		this.id = anotacao.getId();
		this.timestamp = anotacao.getTimestamp();
		this.registrador = anotacao.getRegistrador().getUsername();
		this.descricao = anotacao.getDescricao();
		this.nivelDeImportancia = anotacao.getNivelDeImportancia();
	}
}
