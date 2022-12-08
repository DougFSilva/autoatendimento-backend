package br.com.totemAutoatendimento.aplicacao.comanda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.totemAutoatendimento.aplicacao.comanda.pedido.DadosDePedido;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DadosDeComanda {
    
    private Long id;
	
	private String cartao;

	private String cpfDoCliente;
	
	private String nomeDoCliente;
	
	private List<DadosDePedido> pedidos = new ArrayList<>();
	
	private LocalDateTime abertura;
	
	private LocalDateTime fechamento;
	
	private Boolean aberta;
	
	private TipoPagamento tipoPagamento;

	private BigDecimal valor;
	
	private Integer desconto;

	public DadosDeComanda(Comanda comanda){
		this.id = comanda.getId();
		this.cartao = comanda.getCartao();
		this.cpfDoCliente = comanda.getCliente().getCpf();
		this.nomeDoCliente = comanda.getCliente().getNome();
		this.pedidos = comanda.getPedidos().stream().map(DadosDePedido::new).toList();
		this.abertura = comanda.getAbertura();
		this.fechamento = comanda.getFechamento();
		this.aberta = comanda.getAberta();
		this.tipoPagamento = comanda.getTipoPagamento();
		this.valor = comanda.getValor().multiply(new BigDecimal((100 - comanda.getDesconto())/100));
		this.desconto = comanda.getDesconto();
	}
}
