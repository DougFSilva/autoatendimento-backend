package br.com.totemAutoatendimento.dominio.comanda;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pedido {

	private Long id;
	
	private Mercadoria mercadoria;
	
	private LocalDate data;
	
	private LocalTime tempoPedido;
	
	private LocalTime tempoEntrega;
	
	private Boolean entregue;
}
