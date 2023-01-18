package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscaDadosDeMercadorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.CriaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.EditaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.RemoveMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.UploadImagemMercadoria;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.MercadoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.SubcategoriaEntityAdapter;

@Configuration
public class MercadoriaBeanConfiguration {

	@Autowired
	private MercadoriaEntityAdapter mercadoriaEntityAdapter;

	@Autowired
	private SubcategoriaEntityAdapter subcategoriaEntityAdapter;
	
	@Autowired
	private LoggerAdapter loggerAdapter;

	@Bean
	public CriaMercadoria criaMercadoria() {
		return new CriaMercadoria(mercadoriaEntityAdapter, subcategoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	public RemoveMercadoria removeMercadoria() {
		return new RemoveMercadoria(mercadoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	public EditaMercadoria editaMercadoria() {
		return new EditaMercadoria(mercadoriaEntityAdapter, subcategoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	public BuscaDadosDeMercadorias buscaDadosDeMercadorias() {
		return new BuscaDadosDeMercadorias(mercadoriaEntityAdapter, subcategoriaEntityAdapter);
	}

	@Bean
	public UploadImagemMercadoria uploadImagemDeMercadoria() {
		return new UploadImagemMercadoria(mercadoriaEntityAdapter, loggerAdapter);
	}

}
