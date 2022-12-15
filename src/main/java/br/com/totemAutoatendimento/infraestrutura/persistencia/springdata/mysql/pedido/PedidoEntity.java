package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pedido;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.MercadoriaEntity;
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
	private MercadoriaEntity mercadoria;

	private Integer quantidade;

	private LocalDate data;

	private LocalTime tempoPedido;

	private LocalTime tempoEntrega;

	private Boolean entregue;

	public PedidoEntity(Pedido pedido) {
		this.id = pedido.getId();
		this.mercadoria = new MercadoriaEntity(pedido.getMercadoria());
		this.quantidade = pedido.getQuantidade();
		this.data = pedido.getData();
		this.tempoPedido = pedido.getTempoPedido();
		this.tempoEntrega = pedido.getTempoEntrega();
		this.entregue = pedido.getEntregue();
	}

	public Pedido converterParaPedido() {
		return new Pedido(id, mercadoria.converterParaMercadoria(), quantidade, data, tempoPedido, tempoEntrega,
				entregue);
	}
}
