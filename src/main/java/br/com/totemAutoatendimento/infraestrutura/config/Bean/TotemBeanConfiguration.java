package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.totem.AlteraSenhaDeTotem;
import br.com.totemAutoatendimento.aplicacao.totem.BuscaDadosDeTotem;
import br.com.totemAutoatendimento.aplicacao.totem.CadastraTotem;
import br.com.totemAutoatendimento.aplicacao.totem.DeletaTotem;
import br.com.totemAutoatendimento.aplicacao.totem.EditaTotem;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.TotemEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.UsuarioEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.seguranca.CodificadorBcrypt;

@Configuration
public class TotemBeanConfiguration {

	@Autowired
	private TotemEntityAdapter totemEntityAdapter;
	
	@Autowired
	private UsuarioEntityAdapter usuarioEntityAdapter;
	
	@Autowired
	private CodificadorBcrypt codificadorBcrypt;
	
	@Autowired
	private StandardLogger standardLogger;
	
	@Bean
	CadastraTotem criaTotem() {
		return new CadastraTotem(totemEntityAdapter, usuarioEntityAdapter, codificadorBcrypt, standardLogger);
	}
	
	@Bean
	DeletaTotem removeTotem() {
		return new DeletaTotem(totemEntityAdapter, standardLogger);
	}
	
	@Bean
	EditaTotem editaTotem() {
		return new EditaTotem(totemEntityAdapter,usuarioEntityAdapter,  standardLogger);
	}
	
	@Bean
	BuscaDadosDeTotem buscaDadosDeTotem() {
		return new BuscaDadosDeTotem(totemEntityAdapter);
	}
	
	@Bean
	AlteraSenhaDeTotem alteraSenhaDeTotem() {
		return new AlteraSenhaDeTotem(totemEntityAdapter, codificadorBcrypt, standardLogger);
	}
	
}
