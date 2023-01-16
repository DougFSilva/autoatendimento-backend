package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscaCartaoPeloCodigo {

	private final CartaoRepository repository;

	public BuscaCartaoPeloCodigo(CartaoRepository repository) {
		this.repository = repository;
	}

	public Cartao buscar(String codigo) {
		Optional<Cartao> cartao = repository.buscarPeloCodigo(codigo);
		return cartao.orElseThrow(
				() -> new ObjetoNaoEncontradoException("Cartão com código " + codigo + " não encontrado!"));
	}
}
