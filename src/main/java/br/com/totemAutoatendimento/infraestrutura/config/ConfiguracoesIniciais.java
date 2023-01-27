package br.com.totemAutoatendimento.infraestrutura.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.usuario.CriaUsuarioMaster;

@Configuration
public class ConfiguracoesIniciais implements ApplicationRunner {

	@Autowired
	private CriaUsuarioMaster criaUsuarioMaster;

	@Value("${app.usuario.master.senha}")
	private String senha;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		criaUsuarioMaster.criar(senha);;
	}
	
}
