package br.com.totemAutoatendimento.dominio.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Pedido {

	private Long id;

	private Comanda comanda;

	private Mercadoria mercadoria;
	
	private String mesa;

	private Integer quantidade;

	private LocalDate data;

	private LocalTime tempoPedido;

	private LocalTime tempoEntrega;

	private Boolean entregue;
	
	private BigDecimal valor;
	
	public Pedido(Comanda comanda, Mercadoria mercadoria, String mesa, Integer quantidade) {
		this.comanda = comanda;
		this.mercadoria = mercadoria;
		this.mesa = mesa;
		this.quantidade = quantidade;
		this.data = LocalDate.now();
		this.tempoPedido = LocalTime.now();
		this.entregue = false;
		this.valor = BigDecimal.ZERO;
		if(mercadoria.getPromocao()) {
			BigDecimal valorPromocional = mercadoria.getPrecoPromocional().multiply(new BigDecimal(quantidade));
			this.valor = this.valor.add(valorPromocional);
		}else {
			BigDecimal valor = mercadoria.getPreco().multiply(new BigDecimal(quantidade));
			this.valor = this.valor.add(valor);
		}
	}
	
}
