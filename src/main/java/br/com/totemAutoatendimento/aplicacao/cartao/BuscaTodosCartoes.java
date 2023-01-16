package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.List;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;

public class BuscaTodosCartoes {

	private final CartaoRepository repository; 
	
	public BuscaTodosCartoes(CartaoRepository repository) {
		this.repository = repository;
	}
	
	public List<Cartao> buscar() {
		return repository.buscarTodos();
	}
	
}
