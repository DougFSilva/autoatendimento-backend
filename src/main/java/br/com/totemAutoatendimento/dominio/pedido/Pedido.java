package br.com.totemAutoatendimento.dominio.pedido;

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
}
