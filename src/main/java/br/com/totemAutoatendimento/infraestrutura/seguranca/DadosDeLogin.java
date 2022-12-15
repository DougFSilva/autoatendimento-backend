package br.com.totemAutoatendimento.infraestrutura.seguranca;

import javax.validation.constraints.NotBlank;

public record DadosDeLogin(

    @NotBlank
    String registro,
    
    @NotBlank
    String password
) {
    
}
