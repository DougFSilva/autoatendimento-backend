package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class DeletaCartao {

	private final CartaoRepository repository;

	private final StandardLogger logger;

	public DeletaCartao(CartaoRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public void deletar(String codigo, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Cartao> cartao = repository.buscarPeloCodigo(codigo);
		if (cartao.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cartão com código %s não encontrado!", codigo));
		}
		repository.deletar(cartao.get());
		logger.info(String.format("Cartão com código %s deletado!", cartao.get().getCodigo()), usuarioAutenticado);
	}
}
