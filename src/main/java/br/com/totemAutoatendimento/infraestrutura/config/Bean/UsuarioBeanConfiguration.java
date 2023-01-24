package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.usuario.AlteraSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.BuscaDadosDeUsuarios;
import br.com.totemAutoatendimento.aplicacao.usuario.CriaUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.CriaUsuarioMaster;
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
	private CodificadorBcrypt codificadorBcrypt;
	
	@Autowired
	private LoggerAdapter loggerAdapter;

	@Bean
	CriaUsuario criaUsuario() {
		return new CriaUsuario(usuarioEntityAdapter, codificadorBcrypt, loggerAdapter);
	}
	
	@Bean
	CriaUsuarioMaster criaUsuarioMaster() {
		return new CriaUsuarioMaster(usuarioEntityAdapter, codificadorBcrypt, loggerAdapter);
	}

	@Bean
	RemoveUsuario removeUsuario() {
		return new RemoveUsuario(usuarioEntityAdapter, loggerAdapter);
	}

	@Bean
	EditaUsuario editaUsuario() {
		return new EditaUsuario(usuarioEntityAdapter, loggerAdapter);
	}

	@Bean
	AlteraSenhaDeUsuario alteraSenhaDeUsuario() {
		return new AlteraSenhaDeUsuario(usuarioEntityAdapter, codificadorBcrypt);
	}

	@Bean
	BuscaDadosDeUsuarios buscaDadosDeUsuarios() {
		return new BuscaDadosDeUsuarios(usuarioEntityAdapter);
	}

}
