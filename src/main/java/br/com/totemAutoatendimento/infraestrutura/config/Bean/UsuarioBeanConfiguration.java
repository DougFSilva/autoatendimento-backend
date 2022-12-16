package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.usuario.AlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.BuscarDadosDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.BuscarTodosUsuarios;
import br.com.totemAutoatendimento.aplicacao.usuario.BuscarUsuarioPorRegistro;
import br.com.totemAutoatendimento.aplicacao.usuario.CriarUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.EditarUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.RemoverUsuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.usuario.UsuarioEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.seguranca.CodificadorBcrypt;

@Configuration
public class UsuarioBeanConfiguration {
    
    @Autowired
    private UsuarioEntityRepository repository;

    @Autowired
    private CodificadorBcrypt codificador;

    @Bean
    public CriarUsuario criarUsuario() {
        return new CriarUsuario(repository, codificador);
    }

    @Bean
    public RemoverUsuario removerUsuario() {
        return new RemoverUsuario(repository);
    }

    @Bean
    public EditarUsuario editarUsuario() {
        return new EditarUsuario(repository);
    }

    @Bean
    public AlterarSenhaDeUsuario alterarSenhaDeUsuario() {
        return new AlterarSenhaDeUsuario(repository, codificador);
    }

    @Bean
    public BuscarDadosDeUsuario buscarDadosDeUsuario() {
        return new BuscarDadosDeUsuario(repository);
    }

    @Bean
    public BuscarTodosUsuarios buscarTodosUsuarios() {
        return new BuscarTodosUsuarios(repository);
    }

    @Bean
    public BuscarUsuarioPorRegistro buscarUsuarioPorRegistro() {
        return new BuscarUsuarioPorRegistro(repository);
    }
    
}
