package br.com.totemAutoatendimento.dominio.mercadoria.relatorio;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class RelatorioMercadoriasDeMaiorFaturamento {

	private String codigo;

	private String subcategoria;

	private String descricao;

	private BigDecimal valor;
}
