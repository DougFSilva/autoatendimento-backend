package br.com.totemAutoatendimento.aplicacao.comanda.pedido;

import java.time.LocalDate;
import java.time.LocalTime;

import br.com.totemAutoatendimento.dominio.comanda.Pedido;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDePedido {
    
    private Long id;
	
	private String codigoDaMercadoria;

    private String descricaoDaMercadoria;

    private Integer quantidade;
	
	private LocalDate data;
	
	private LocalTime tempoPedido;
	
	private LocalTime tempoEntrega;
	
	private Boolean entregue;

    public DadosDePedido(Pedido pedido){
        this.id = pedido.getId();
        this.codigoDaMercadoria = pedido.getMercadoria().getCodigo();
        this.descricaoDaMercadoria = pedido.getMercadoria().getDescricao();
        this.quantidade = pedido.getQuantidade();
        this.data = pedido.getData();
        this.tempoPedido = pedido.getTempoPedido();
        this.tempoEntrega = pedido.getTempoEntrega();
        this.entregue =pedido.getEntregue();
    }
}
