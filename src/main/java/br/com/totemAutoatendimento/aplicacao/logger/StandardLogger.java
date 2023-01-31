package br.com.totemAutoatendimento.aplicacao.logger;

import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class StandardLogger {

	private final SystemLogger logger;

	public StandardLogger(SystemLogger logger) {
		this.logger = logger;
	}

	public void error(String mensagem, Usuario usuario) {
		logger.error(String.format("Usuário %s - %s", usuario.getUsername(), mensagem));
	}

	public void warn(String mensagem, Usuario usuario) {
		logger.warn(String.format("Usuário %s - %s", usuario.getUsername(), mensagem));
	}

	public void info(String mensagem, Usuario usuario) {
		logger.info(String.format("Usuário %s - %s", usuario.getUsername(), mensagem));
	}

	public void debug(String mensagem, Usuario usuario) {
		logger.debug(String.format("Usuário %s - %s", usuario.getUsername(), mensagem));
	}

	public void trace(String mensagem, Usuario usuario) {
		logger.trace(String.format("Usuário %s - %s", usuario.getUsername(), mensagem));
	}
}
