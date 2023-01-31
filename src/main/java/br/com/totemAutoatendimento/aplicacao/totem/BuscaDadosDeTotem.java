package br.com.totemAutoatendimento.aplicacao.totem;

import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.aplicacao.totem.dto.DadosDeTotem;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.totem.TotemRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDeTotem {

	private final TotemRepository repository;

	public BuscaDadosDeTotem(TotemRepository repository) {
		this.repository = repository;
	}
	
	public DadosDeTotem buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Totem> totem = repository.buscarPeloId(id);
		if(totem.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Totem com id %d não encotrado!", id));
		}
		return new DadosDeTotem(totem.get());
	}
	
	public DadosDeTotem buscarPeloIdentificador(String identificador, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Totem> totem = repository.buscarPeloIdentificador(identificador);
		if(totem.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Totem com identificador %s não encotrado!", identificador));
		}
		return new DadosDeTotem(totem.get());
	}
	
	public DadosDeTotem buscarPeloUsuario(Long usuarioId, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Totem> totem = repository.buscarPeloUsuario(usuarioId);
		if(totem.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Totem com usuario de id %d não encotrado!", usuarioId));
		}
		return new DadosDeTotem(totem.get());
	}
	
	public List<DadosDeTotem> buscarTodos(Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		return repository.buscarTodos().stream().map(DadosDeTotem::new).toList();
	}
	
}
