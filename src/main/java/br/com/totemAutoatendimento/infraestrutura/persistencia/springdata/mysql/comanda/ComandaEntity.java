package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
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

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String cartao;

	private Cliente cliente;

	private List<Pedido> pedidos;

	private LocalDateTime abertura;

	private LocalDateTime fechamento;

	private Boolean aberta;

	private TipoPagamento tipoPagamento;

	private Integer desconto;
}
