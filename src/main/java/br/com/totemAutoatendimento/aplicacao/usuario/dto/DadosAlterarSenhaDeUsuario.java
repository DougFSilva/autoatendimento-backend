package br.com.totemAutoatendimento.aplicacao.usuario.dto;

import javax.validation.constraints.NotBlank;

public record DadosAlterarSenhaDeUsuario(@NotBlank String senhaAtual, @NotBlank String novaSenha) {

}
