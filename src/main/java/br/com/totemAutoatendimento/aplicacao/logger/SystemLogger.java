package br.com.totemAutoatendimento.aplicacao.logger;

public interface SystemLogger {

	void error(String mensagem);
	
	void warn(String mensagem);
	
	void info(String mensagem);
	
	void debug(String mensagem);
	
	void trace(String mensagem);

}
