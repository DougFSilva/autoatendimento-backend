package br.com.totemAutoatendimento.aplicacao.usuario;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.dominio.usuario.UsuarioRepository;

public class RemoveUsuario {

	private final UsuarioRepository repository;
	
	private final SystemLogger logger;

	public RemoveUsuario(UsuarioRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	@Transactional
	public void remover(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		BuscaUsuarioPeloId buscaUsuarioPeloId = new BuscaUsuarioPeloId(repository);
		repository.remover(buscaUsuarioPeloId.buscar(id));
		logger.info(String.format("Usuário %s -  Usuário com id %d removido!", usuarioAutenticado.getRegistro(), id));
	}
}
