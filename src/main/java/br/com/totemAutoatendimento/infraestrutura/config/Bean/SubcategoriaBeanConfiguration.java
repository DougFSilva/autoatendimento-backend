package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscaSubcategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.CriaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.EditaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.RemoveSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.UploadImagemDaSubcategoria;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.CategoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.MercadoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.SubcategoriaEntityAdapter;

@Configuration
public class SubcategoriaBeanConfiguration {

	@Autowired
	private SubcategoriaEntityAdapter subcategoriaEntityAdapter;

	@Autowired
	private CategoriaEntityAdapter categoriaEntityAdapter;

	@Autowired
	private MercadoriaEntityAdapter mercadoriaEntityAdapter;
	
	@Autowired
	private LoggerAdapter loggerAdapter;

	@Bean
	CriaSubcategoria criaSubcategoria() {
		return new CriaSubcategoria(subcategoriaEntityAdapter, categoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	RemoveSubcategoria removeSubcategoria() {
		return new RemoveSubcategoria(subcategoriaEntityAdapter, mercadoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	EditaSubcategoria editaSubcategoria() {
		return new EditaSubcategoria(subcategoriaEntityAdapter, categoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	BuscaSubcategorias buscaSubcategorias() {
		return new BuscaSubcategorias(subcategoriaEntityAdapter, categoriaEntityAdapter);
	}

	@Bean
	UploadImagemDaSubcategoria uploadImagemDaSubcategoria() {
		return new UploadImagemDaSubcategoria(subcategoriaEntityAdapter, loggerAdapter);
	}
}
