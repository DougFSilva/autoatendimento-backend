package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.BuscaDadosDeMercadorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.CadastraMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.DeletaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.EditaMercadoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.UploadImagemMercadoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.MercadoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.SubcategoriaEntityAdapter;

@Configuration
public class MercadoriaBeanConfiguration {

	@Autowired
	private MercadoriaEntityAdapter mercadoriaEntityAdapter;

	@Autowired
	private SubcategoriaEntityAdapter subcategoriaEntityAdapter;
	
	@Autowired
	private StandardLogger standardLogger;

	@Bean
	CadastraMercadoria criaMercadoria() {
		return new CadastraMercadoria(mercadoriaEntityAdapter, subcategoriaEntityAdapter, standardLogger);
	}

	@Bean
	DeletaMercadoria removeMercadoria() {
		return new DeletaMercadoria(mercadoriaEntityAdapter, standardLogger);
	}

	@Bean
	EditaMercadoria editaMercadoria() {
		return new EditaMercadoria(mercadoriaEntityAdapter, subcategoriaEntityAdapter, standardLogger);
	}

	@Bean
	BuscaDadosDeMercadorias buscaDadosDeMercadorias() {
		return new BuscaDadosDeMercadorias(mercadoriaEntityAdapter, subcategoriaEntityAdapter);
	}

	@Bean
	UploadImagemMercadoria uploadImagemDeMercadoria() {
		return new UploadImagemMercadoria(mercadoriaEntityAdapter, standardLogger);
	}

}
