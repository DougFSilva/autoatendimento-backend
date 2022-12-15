package br.com.totemAutoatendimento.dominio.usuario;

public interface AutorizacaoDeAcesso {
    
    Boolean permissaoDeAdministrador();

    Boolean permissaoDeFuncionario();
}
