package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.anotacao.BuscaDadosDeAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.CriaAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.EditaAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.RemoveAnotacao;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.AnotacaoEntityAdapter;

@Configuration
public class AnotacaoBeanConfiguration {

	@Autowired
	private AnotacaoEntityAdapter anotacaoEntityAdapter;
	
	@Autowired
	private LoggerAdapter loggerAdapter;
	
	@Bean
	CriaAnotacao criaAnotacao() {
		return new CriaAnotacao(anotacaoEntityAdapter, loggerAdapter);
	}
	
	@Bean
	RemoveAnotacao removeAnotacao () {
		return new RemoveAnotacao(anotacaoEntityAdapter, loggerAdapter);
	}
	
	@Bean
	EditaAnotacao editaAnotacao() {
		return new EditaAnotacao(anotacaoEntityAdapter, loggerAdapter);
	}
	
	@Bean
	BuscaDadosDeAnotacao buscaDadosDeAnotacao() {
		return new BuscaDadosDeAnotacao(anotacaoEntityAdapter);
	}
}
