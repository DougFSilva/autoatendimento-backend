package br.com.totemAutoatendimento.aplicacao.comanda.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DadosDeComanda {

	private Long id;

	private String cartao;

	private String cpfDoCliente;

	private String nomeDoCliente;

	private LocalDateTime abertura;

	private LocalDateTime fechamento;

	private Boolean aberta;

	private String tipoPagamento;

	private BigDecimal valor;

	private Float desconto;

	public DadosDeComanda(Comanda comanda) {
		this.id = comanda.getId();
		this.cartao = comanda.getCartao().getCodigo();
		this.cpfDoCliente = comanda.getCliente().getCpf();
		this.nomeDoCliente = comanda.getCliente().getNome();
		this.abertura = comanda.getAbertura();
		this.fechamento = comanda.getFechamento();
		this.aberta = comanda.getAberta();
		this.tipoPagamento = comanda.getTipoPagamento().getDescricao();
		this.valor = comanda.getValor();
		this.desconto = comanda.getDesconto();
	}
}
