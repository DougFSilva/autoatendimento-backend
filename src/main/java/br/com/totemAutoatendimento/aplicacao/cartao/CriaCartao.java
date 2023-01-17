package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;

@PreAuthorize("hasRole('ADMIN')")
public class CriaCartao {

	private final CartaoRepository repository; 
	
	public CriaCartao(CartaoRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public Cartao criar(String codigo) {
		Optional<Cartao> cartao = repository.buscarPeloCodigo(codigo);
		if(cartao.isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException("Cartão com código " + codigo + " já cadastrado!");
		}
		Cartao novoCartao = new Cartao(codigo);
		return repository.criar(novoCartao);
	}
}
