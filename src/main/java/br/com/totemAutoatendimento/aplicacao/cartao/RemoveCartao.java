package br.com.totemAutoatendimento.aplicacao.cartao;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;

@PreAuthorize("hasRole('ADMIN')")
public class RemoveCartao {

private final CartaoRepository repository; 
	
	public RemoveCartao(CartaoRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public void remover(String codigo) {
		BuscaCartaoPeloCodigo buscarCartaoPeloCodigo = new BuscaCartaoPeloCodigo(repository);
		Cartao cartao = buscarCartaoPeloCodigo.buscar(codigo);
		repository.remover(cartao);
	}
}
