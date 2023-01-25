package br.com.totemAutoatendimento.dominio.mercadoria.relatorio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RelatorioMercadoriasMaisVendidas {

	private String codigo;
	
	private String subcategoria;
	
	private String descricao;
	
	private Double quantidade;
}
