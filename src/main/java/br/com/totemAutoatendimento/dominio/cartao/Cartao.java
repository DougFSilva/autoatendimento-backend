package br.com.totemAutoatendimento.dominio.cartao;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "codigo")
@ToString
public class Cartao {

	private String codigo;
}
