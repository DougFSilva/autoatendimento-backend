package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.anotacao.BuscaDadosDeAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.CriaAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.DeletaAnotacao;
import br.com.totemAutoatendimento.aplicacao.anotacao.EditaAnotacao;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.AnotacaoEntityAdapter;

@Configuration
public class AnotacaoBeanConfiguration {

	@Autowired
	private AnotacaoEntityAdapter anotacaoEntityAdapter;
	
	@Autowired
	private StandardLogger standardLogger;
	
	@Bean
	CriaAnotacao criaAnotacao() {
		return new CriaAnotacao(anotacaoEntityAdapter, standardLogger);
	}
	
	@Bean
	DeletaAnotacao removeAnotacao () {
		return new DeletaAnotacao(anotacaoEntityAdapter, standardLogger);
	}
	
	@Bean
	EditaAnotacao editaAnotacao() {
		return new EditaAnotacao(anotacaoEntityAdapter, standardLogger);
	}
	
	@Bean
	BuscaDadosDeAnotacao buscaDadosDeAnotacao() {
		return new BuscaDadosDeAnotacao(anotacaoEntityAdapter);
	}
}
