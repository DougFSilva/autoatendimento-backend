package br.com.totemAutoatendimento.dominio.comanda;

import java.time.LocalDate;
import java.time.LocalTime;

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
	
	private Mercadoria mercadoria;

	private Integer quantidade;
	
	private LocalDate data;
	
	private LocalTime tempoPedido;
	
	private LocalTime tempoEntrega;
	
	private Boolean entregue;
}
