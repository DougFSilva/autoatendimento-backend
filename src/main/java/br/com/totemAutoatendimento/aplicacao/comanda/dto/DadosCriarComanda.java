package br.com.totemAutoatendimento.aplicacao.comanda.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record DadosCriarComanda(

		@NotBlank String codigoCartao,

		@NotNull Long clienteId) {

}
