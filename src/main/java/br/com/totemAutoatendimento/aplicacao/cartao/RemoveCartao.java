package br.com.totemAutoatendimento.aplicacao.cartao;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cartao.Cartao;
import br.com.totemAutoatendimento.dominio.cartao.CartaoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveCartao {

	private final CartaoRepository repository; 
	
	private final SystemLogger logger;
	
	public RemoveCartao(CartaoRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	@Transactional
	public void remover(String codigo, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		BuscaCartaoPeloCodigo buscarCartaoPeloCodigo = new BuscaCartaoPeloCodigo(repository);
		Cartao cartao = buscarCartaoPeloCodigo.buscar(codigo);
		repository.remover(cartao);
		logger.info(String.format("Usuário %s - Cartão com código %s removido!", usuarioAutenticado.getRegistro(), cartao.getCodigo()));
	}
}
