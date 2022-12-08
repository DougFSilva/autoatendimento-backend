package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.AlterarSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.BuscarDadosDeUsuario;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.BuscarTodosUsuarios;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.CriarUsuario;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.EditarUsuario;
import br.com.totemAutoatendimento.aplicacao.pessoa.usuario.RemoverUsuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.usuario.UsuarioEntityRepository;
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
    
}
