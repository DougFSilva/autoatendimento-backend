package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.funcionario.BuscaDadosDeFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.CadastraFuncionario;
import br.com.totemAutoatendimento.aplicacao.funcionario.EditaFuncionario;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.funcionario.DeletaFuncionario;
import br.com.totemAutoatendimento.aplicacao.usuario.AlteraSenhaDeUsuario;
import br.com.totemAutoatendimento.aplicacao.usuario.CriaUsuarioMaster;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.FuncionarioEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.UsuarioEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.seguranca.CodificadorBcrypt;

@Configuration
public class FuncionarioBeanConfiguration {

	@Autowired
	private FuncionarioEntityAdapter funcionarioEntityAdapter;
	
	@Autowired
	private UsuarioEntityAdapter usuarioEntityAdapter;
	
	@Autowired
	private CodificadorBcrypt codificadorBcrypt;
	
	@Autowired
	private LoggerAdapter loggerAdapter;
	
	@Autowired
	private StandardLogger standardLogger;
	
	@Bean
	CadastraFuncionario criaFuncionario() {
		return new CadastraFuncionario(funcionarioEntityAdapter, usuarioEntityAdapter, codificadorBcrypt, standardLogger);
	}
	
	@Bean
	CriaUsuarioMaster criaUsuarioMaster() {
		return new CriaUsuarioMaster(usuarioEntityAdapter, codificadorBcrypt, loggerAdapter);
	}
	
	@Bean
	DeletaFuncionario removeFuncionario() {
		return new DeletaFuncionario(funcionarioEntityAdapter, standardLogger);
	}
	
	@Bean
	EditaFuncionario editaFuncionario() {
		return new EditaFuncionario(funcionarioEntityAdapter, usuarioEntityAdapter, standardLogger);
	}
	
	@Bean
	BuscaDadosDeFuncionario buscaDadosDeFuncionario() {
		return new BuscaDadosDeFuncionario(funcionarioEntityAdapter);
	}
	
	@Bean
	AlteraSenhaDeUsuario alteraSenhaDeUsuario() {
		return new AlteraSenhaDeUsuario(usuarioEntityAdapter, codificadorBcrypt, standardLogger);
	}
}
