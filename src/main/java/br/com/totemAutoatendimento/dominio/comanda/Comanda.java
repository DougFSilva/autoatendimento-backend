package br.com.totemAutoatendimento.dominio.comanda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
public class Comanda {

	private Long id;
	
	private String cartao;
	
	private Cliente cliente;
	
	private List<Pedido> pedidos = new ArrayList<>();
	
	private LocalDateTime abertura;
	
	private LocalDateTime fechamento;
	
	private Boolean aberta;
	
	private TipoPagamento tipoPagamento;

	private BigDecimal valor;
	
	private Integer desconto;

	public Comanda(String cartao, Cliente cliente){
		this.cartao = cartao;
		this.cliente = cliente;
		this.abertura = LocalDateTime.now();
		this.fechamento = null;
		this.aberta = true;
		this.tipoPagamento = null;
		this.valor = BigDecimal.ZERO;
		this.desconto = 0;
	}

	public void adicionarPedido(Pedido pedido){
		this.pedidos.add(pedido);
		BigDecimal valor = pedido.getMercadoria().getPreco().multiply(new BigDecimal(pedido.getQuantidade()));
		this.valor = this.valor.add(valor);
	}

	public void removerPedido(Pedido pedido){
		this.pedidos.remove(pedido);
		BigDecimal valor = pedido.getMercadoria().getPreco().multiply(new BigDecimal(pedido.getQuantidade()));
		this.valor = this.valor.subtract(valor);
	}

	public void aplicarDesconto(){
		this.valor = this.valor.multiply(new BigDecimal((100 - this.desconto)/100));
	}
}
