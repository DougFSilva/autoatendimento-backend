package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.usuario.AlteraSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.BuscaDadosDeUsuarios;
import br.com.totemAutoatendimento.aplicacao.usuario.CriaUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.EditaUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.RemoveUsuario;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.UsuarioEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.seguranca.CodificadorBcrypt;

@Configuration
public class UsuarioBeanConfiguration {

	@Autowired
	private UsuarioEntityAdapter usuarioEntityAdapter;

	@Autowired
	private CodificadorBcrypt codificador;
	
	@Autowired
	private LoggerAdapter loggerAdapter;

	@Bean
	public CriaUsuario criaUsuario() {
		return new CriaUsuario(usuarioEntityAdapter, codificador, loggerAdapter);
	}

	@Bean
	public RemoveUsuario removeUsuario() {
		return new RemoveUsuario(usuarioEntityAdapter, loggerAdapter);
	}

	@Bean
	public EditaUsuario editaUsuario() {
		return new EditaUsuario(usuarioEntityAdapter, loggerAdapter);
	}

	@Bean
	public AlteraSenhaDeUsuario alteraSenhaDeUsuario() {
		return new AlteraSenhaDeUsuario(usuarioEntityAdapter, codificador);
	}

	@Bean
	public BuscaDadosDeUsuarios buscaDadosDeUsuarios() {
		return new BuscaDadosDeUsuarios(usuarioEntityAdapter);
	}

}
