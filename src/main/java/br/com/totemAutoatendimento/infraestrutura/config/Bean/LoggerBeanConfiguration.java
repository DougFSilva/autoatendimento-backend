package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;

@Configuration
public class LoggerBeanConfiguration {

	@Autowired
	private LoggerAdapter loggerAdapter;
	
	@Bean
	StandardLogger standardLogger() {
		return new StandardLogger(loggerAdapter);
	}
}
