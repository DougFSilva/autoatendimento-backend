package br.com.totemAutoatendimento.infraestrutura.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.usuario.CriaUsuarioMaster;
import br.com.totemAutoatendimento.aplicacao.usuario.dto.DadosCriarUsuario;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;

@Configuration
public class ConfiguracoesIniciais implements ApplicationRunner {

	@Autowired
	private CriaUsuarioMaster criaUsuarioMaster;

	@Value("${app.usuario.master.username}")
	private String registro;

	@Value("${app.usuario.master.senha}")
	private String senha;

	@Value("${app.usuario.master.email}")
	private String email;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		criarUsuarioMaster();
	}
	
	private void criarUsuarioMaster() {
		String nome = "Master";
		String cpf = "00000000010";
		DadosCriarUsuario dadosCriarUsuario = new DadosCriarUsuario(nome, cpf, registro, email, senha,
				Arrays.asList(TipoPerfil.ADMINISTRADOR));
		criaUsuarioMaster.criar(dadosCriarUsuario);
	}

}
