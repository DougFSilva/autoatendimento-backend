package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.Avaliacao.BuscaDadosDeAvaliacao;
import br.com.totemAutoatendimento.aplicacao.Avaliacao.CriaAvaliacao;
import br.com.totemAutoatendimento.aplicacao.Avaliacao.DeletaAvaliacao;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.AvaliacaoEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.TotemEntityAdapter;

@Configuration
public class AvaliacaoBeanConfiguration {

	@Autowired
	private AvaliacaoEntityAdapter avaliacaoEntityAdapter;
	
	@Autowired
	private TotemEntityAdapter totemEntityAdapter;
	
	@Autowired
	private StandardLogger standardLogger;
	
	@Bean
	CriaAvaliacao criaAvaliacao() {
		return new CriaAvaliacao(avaliacaoEntityAdapter, totemEntityAdapter);
	}
	
	@Bean
	DeletaAvaliacao deletaAvaliacao() {
		return new DeletaAvaliacao(avaliacaoEntityAdapter, standardLogger);
	}
	
	@Bean
	BuscaDadosDeAvaliacao buscaDadosDeAvaliacao() {
		return new BuscaDadosDeAvaliacao(avaliacaoEntityAdapter);
	}
	
}
