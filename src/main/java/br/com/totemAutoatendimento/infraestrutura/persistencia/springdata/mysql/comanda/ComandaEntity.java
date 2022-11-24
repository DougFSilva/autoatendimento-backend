package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import java.time.LocalDateTime;
import java.util.List;

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
public class ComandaEntity {

	private Long id;

	private String cartao;

	private Cliente cliente;

	private List<PedidoEntity> pedidos;

	private LocalDateTime abertura;

	private LocalDateTime fechamento;

	private Boolean aberta;

	private TipoPagamento tipoPagamento;

	private Integer desconto;
}
