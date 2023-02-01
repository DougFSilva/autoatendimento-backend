package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CadastraCartao {

	private final CartaoRepository repository;

	private final StandardLogger logger;

	public CadastraCartao(CartaoRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public Cartao cadastrar(String codigo, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Cartao> cartao = repository.buscarPeloCodigo(codigo);
		if (cartao.isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Cartão com código %s já criado!", codigo));
		}
		Cartao novoCartao = new Cartao(codigo);
		Cartao cartaoCadastrado = repository.salvar(novoCartao);
		logger.info(String.format("Cartão com código %s cadastrado!", cartaoCadastrado.getCodigo()), usuarioAutenticado);
		return cartaoCadastrado;
	}
}
