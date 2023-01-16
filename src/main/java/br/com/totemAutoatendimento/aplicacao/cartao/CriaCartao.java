package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

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
			throw new ObjetoNaoEncontradoException("Cartão com código " + codigo + " já cadastrado!");
		}
		return repository.criar(cartao.get());
	}
}
