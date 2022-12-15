package br.com.totemAutoatendimento.dominio.comanda;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
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

	private Float desconto;

	public Comanda(String cartao, Cliente cliente) {
		this.cartao = cartao;
		this.cliente = cliente;
		this.abertura = LocalDateTime.now();
		this.fechamento = null;
		this.aberta = true;
		this.tipoPagamento = TipoPagamento.NAO_PAGO;
		this.valor = BigDecimal.ZERO.setScale(2);
		this.desconto = 0f;
	}

	public void adicionarPedido(Pedido pedido) {
		BigDecimal valor = BigDecimal.ZERO;
		if(pedido.getMercadoria().getPromocao()){
			valor = pedido.getMercadoria().getPrecoPromocional().multiply(new BigDecimal(pedido.getQuantidade().toString()));
		}else {
			valor = pedido.getMercadoria().getPreco().multiply(new BigDecimal(pedido.getQuantidade().toString()));
		}
		this.valor = this.valor.add(valor);
		this.pedidos.add(pedido);
	}

	public void removerPedido(Pedido pedido) {
		BigDecimal valor = BigDecimal.ZERO;
		if(pedido.getMercadoria().getPromocao()){
			valor = pedido.getMercadoria().getPrecoPromocional().multiply(new BigDecimal(pedido.getQuantidade().toString()));
		}else {
			valor = pedido.getMercadoria().getPreco().multiply(new BigDecimal(pedido.getQuantidade().toString()));
		}
		this.valor = this.valor.subtract(valor);
		this.pedidos.remove(pedido);
	}

	public void aplicarDesconto(Float desconto) {
		if(!this.aberta){
			throw new RegrasDeNegocioException("Não é possível aplicar desconto em comanda fechada!");
		}
		this.desconto = desconto;
		Float descontoPercentual = ((100 - this.desconto) / 100);
		this.valor = this.valor.multiply(new BigDecimal(descontoPercentual.toString())).setScale(2, RoundingMode.HALF_EVEN);
	}

	public void removerDesconto() {
		if(!this.aberta){
			throw new RegrasDeNegocioException("Não é possível remover desconto de comanda fechada!");
		}
		Float descontoPercentual = (100 / (100 - this.desconto));
		this.valor = this.valor.multiply(new BigDecimal(descontoPercentual.toString())).setScale(2, RoundingMode.HALF_EVEN);
	}
}
