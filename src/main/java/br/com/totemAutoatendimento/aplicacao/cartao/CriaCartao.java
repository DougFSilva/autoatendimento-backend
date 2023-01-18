package br.com.totemAutoatendimento.aplicacao.cartao;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CriaCartao {

	private final CartaoRepository repository;

	private final SystemLogger logger;

	public CriaCartao(CartaoRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public Cartao criar(String codigo, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Cartao> cartao = repository.buscarPeloCodigo(codigo);
		if (cartao.isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Cartão com código %s já criado!", codigo));
		}
		Cartao novoCartao = new Cartao(codigo);
		Cartao cartaoCriado = repository.criar(novoCartao);
		logger.info(String.format("Usuario %s - Cartão com código %s criado!", usuarioAutenticado.getRegistro(),
				cartaoCriado.getCodigo()));
		return cartaoCriado;
	}
}
