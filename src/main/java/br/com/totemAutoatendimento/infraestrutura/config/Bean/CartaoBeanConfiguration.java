package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.cartao.BuscaTodosCartoes;
import br.com.totemAutoatendimento.aplicacao.cartao.CadastraCartao;
import br.com.totemAutoatendimento.aplicacao.cartao.DeletaCartao;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.CartaoEntityAdapter;

@Configuration
public class CartaoBeanConfiguration {

	@Autowired
	private CartaoEntityAdapter cartaoEntityAdapter;
	
	@Autowired
	private StandardLogger standardLogger;

	@Bean
	CadastraCartao criaCartao() {
		return new CadastraCartao(cartaoEntityAdapter, standardLogger);
	}
	
	@Bean
	DeletaCartao removeCartao() {
		return new DeletaCartao(cartaoEntityAdapter, standardLogger);
	}
	
	@Bean
	BuscaTodosCartoes buscaTodosCartoes() {
		return new BuscaTodosCartoes(cartaoEntityAdapter);
	}
}
