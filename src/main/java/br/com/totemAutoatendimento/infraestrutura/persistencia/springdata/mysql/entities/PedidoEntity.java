package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "pedidos")
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private ComandaEntity comanda;

	@ManyToOne
	private MercadoriaEntity mercadoria;
	
	private String mesa;

	private Float quantidade;

	private LocalDate data;

	private LocalTime tempoPedido;

	private LocalTime tempoEntrega;

	private Boolean entregue;
	
	private BigDecimal valor;

}
