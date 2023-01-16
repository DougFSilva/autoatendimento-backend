package br.com.totemAutoatendimento.dominio.comanda;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
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
public class Comanda {

	private Long id;

	private Cartao cartao;

	private Cliente cliente;

	private LocalDateTime abertura;

	private LocalDateTime fechamento;

	private Boolean aberta;

	private TipoPagamento tipoPagamento;

	private BigDecimal valor;

	private Float desconto;

	public Comanda(Cartao cartao, Cliente cliente) {
		this.cartao = cartao;
		this.cliente = cliente;
		this.abertura = LocalDateTime.now();
		this.fechamento = null;
		this.aberta = true;
		this.tipoPagamento = TipoPagamento.NAO_PAGO;
		this.valor = BigDecimal.ZERO.setScale(2);
		this.desconto = 0f;
	}

	public void aplicarDesconto(Float desconto) {
		if (!this.aberta) {
			throw new RegrasDeNegocioException("Não é possível aplicar desconto em comanda fechada!");
		}
		this.desconto = desconto;
		Float descontoPercentual = ((100 - this.desconto) / 100);
		this.valor = this.valor.multiply(new BigDecimal(descontoPercentual.toString())).setScale(2,
				RoundingMode.HALF_EVEN);
	}

	public void removerDesconto() {
		if (!this.aberta) {
			throw new RegrasDeNegocioException("Não é possível remover desconto de comanda fechada!");
		}
		Float descontoPercentual = (100 / (100 - this.desconto));
		this.valor = this.valor.multiply(new BigDecimal(descontoPercentual.toString())).setScale(2,
				RoundingMode.HALF_EVEN);
	}

	public void adicionarValor(Pedido pedido) {
		if(pedido.getMercadoria().getPromocao()) {
			BigDecimal valorPromocional = pedido.getMercadoria().getPrecoPromocional().multiply(new BigDecimal(pedido.getQuantidade()));
			this.valor = this.valor.add(valorPromocional);
		}else {
			BigDecimal valor = pedido.getMercadoria().getPreco().multiply(new BigDecimal(pedido.getQuantidade()));
			this.valor = this.valor.add(valor);
		}
	}
	
	public void removerValor(Pedido pedido) {
		if(pedido.getMercadoria().getPromocao()) {
			BigDecimal valorPromocional = pedido.getMercadoria().getPrecoPromocional().multiply(new BigDecimal(pedido.getQuantidade()));
			this.valor = this.valor.subtract(valorPromocional);
		}else {
			BigDecimal valor = pedido.getMercadoria().getPreco().multiply(new BigDecimal(pedido.getQuantidade()));
			this.valor = this.valor.subtract(valor);
		}
	}
}
