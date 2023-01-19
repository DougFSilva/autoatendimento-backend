package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.List;

import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaTodosCartoes {

	private final CartaoRepository repository; 
	
	public BuscaTodosCartoes(CartaoRepository repository) {
		this.repository = repository;
	}
	
	public List<Cartao> buscar(Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		return repository.buscarTodos();
	}
	
}
