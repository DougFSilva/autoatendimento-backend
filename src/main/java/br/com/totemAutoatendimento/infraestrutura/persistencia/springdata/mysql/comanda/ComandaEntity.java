package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.cliente.ClienteEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pedido.PedidoEntity;
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

	private String cartao;

	@ManyToOne(cascade = CascadeType.MERGE)
	private ClienteEntity cliente;

	@OneToMany(cascade = CascadeType.ALL)
	private List<PedidoEntity> pedidos = new ArrayList<>();

	private LocalDateTime abertura;

	private LocalDateTime fechamento;

	private Boolean aberta;

	@Enumerated(EnumType.STRING)
	private TipoPagamento tipoPagamento;

	private BigDecimal valor;

	private Float desconto;

	public ComandaEntity(Comanda comanda) {
		this.id = comanda.getId();
		this.cartao = comanda.getCartao();
		this.cliente = new ClienteEntity(comanda.getCliente());
		this.pedidos = comanda.getPedidos().stream().map(PedidoEntity::new).toList();
		this.abertura = comanda.getAbertura();
		this.fechamento = comanda.getFechamento();
		this.aberta = comanda.getAberta();
		this.tipoPagamento = comanda.getTipoPagamento();
		this.valor = comanda.getValor();
		this.desconto = comanda.getDesconto();
	}

	public Comanda converterParaComanda() {
		List<Pedido> pedidos = this.pedidos.stream().map(PedidoEntity::converterParaPedido).collect(Collectors.toList());
		return new Comanda(id, cartao, cliente.converterParaCliente(), pedidos, abertura, fechamento, aberta,
				tipoPagamento, valor, desconto);
	}
}
