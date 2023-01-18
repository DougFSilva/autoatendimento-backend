package br.com.totemAutoatendimento.infraestrutura.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;

@Service
public class LoggerAdapter implements SystemLogger {

	private Logger logger = LoggerFactory.getLogger(LoggerAdapter.class);


	@Override
	public void error(String mensagem) {
		logger.error(mensagem);
	}

	@Override
	public void warn(String mensagem) {
		logger.warn(mensagem);
	}
	
	@Override
	public void info(String mensagem) {
		logger.info(mensagem);
	}

	@Override
	public void debug(String mensagem) {
		logger.debug(mensagem);
	}

	@Override
	public void trace(String mensagem) {
		logger.trace(mensagem);
	}

}
