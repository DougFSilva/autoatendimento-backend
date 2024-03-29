package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "comandas")
public class ComandaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "cartao")
	@ManyToOne
	private CartaoEntity cartao;

	@OneToOne
	private ClienteEntity cliente;

	private LocalDateTime abertura;

	private LocalDateTime fechamento;

	private Boolean aberta;

	@Enumerated(EnumType.STRING)
	private TipoPagamento tipoPagamento;

	private BigDecimal valor;

	private Float desconto;

}
