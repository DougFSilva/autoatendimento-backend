package br.com.totemAutoatendimento.aplicacao.usuario;

import java.util.Arrays;
import java.util.List;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.dominio.usuario.CodificadorDeSenha;
import br.com.totemAutoatendimento.dominio.usuario.Password;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class CriaUsuarioMaster {

	private final UsuarioRepository repository;

	private final CodificadorDeSenha codificadorDeSenha;
	
	private final SystemLogger logger;
	
	public CriaUsuarioMaster(UsuarioRepository repository, CodificadorDeSenha codificadorDeSenha, final SystemLogger logger) {
		this.repository = repository;
		this.codificadorDeSenha = codificadorDeSenha;
		this.logger = logger;
	}
	
	public void criar(String senha) {
		String username = "master";
		Password password = new Password(senha, codificadorDeSenha);
		List<Perfil> perfis = Arrays.asList(new Perfil(TipoPerfil.ADMINISTRADOR), new Perfil(TipoPerfil.FUNCIONARIO));
		Usuario usuario = new Usuario(null, username, password, perfis);
		if(repository.buscarPeloUsername(username).isEmpty()) {
			repository.salvar(usuario);
			logger.info("Sistema - Usuário Master Criado!");
		};
	}
	
	
}
