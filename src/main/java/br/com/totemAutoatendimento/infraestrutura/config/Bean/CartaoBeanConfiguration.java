package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.cartao.BuscaTodosCartoes;
import br.com.totemAutoatendimento.aplicacao.cartao.CriaCartao;
import br.com.totemAutoatendimento.aplicacao.cartao.RemoveCartao;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.CartaoEntityAdapter;

@Configuration
public class CartaoBeanConfiguration {

	@Autowired
	private CartaoEntityAdapter cartaoEntityAdapter;
	
	@Autowired
	private LoggerAdapter loggerAdapter;

	@Bean
	public CriaCartao criaCartao() {
		return new CriaCartao(cartaoEntityAdapter, loggerAdapter);
	}
	
	@Bean
	public RemoveCartao removeCartao() {
		return new RemoveCartao(cartaoEntityAdapter);
	}
	
	@Bean
	public BuscaTodosCartoes buscaTodosCartoes() {
		return new BuscaTodosCartoes(cartaoEntityAdapter);
	}
}
