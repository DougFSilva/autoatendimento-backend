package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.BuscaSubcategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.CriaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.DeletaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.EditaSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.UploadImagemDaSubcategoria;
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
	private StandardLogger standardLogger;

	@Bean
	CriaSubcategoria criaSubcategoria() {
		return new CriaSubcategoria(subcategoriaEntityAdapter, categoriaEntityAdapter, standardLogger);
	}

	@Bean
	DeletaSubcategoria removeSubcategoria() {
		return new DeletaSubcategoria(subcategoriaEntityAdapter, mercadoriaEntityAdapter, standardLogger);
	}

	@Bean
	EditaSubcategoria editaSubcategoria() {
		return new EditaSubcategoria(subcategoriaEntityAdapter, categoriaEntityAdapter, standardLogger);
	}

	@Bean
	BuscaSubcategorias buscaSubcategorias() {
		return new BuscaSubcategorias(subcategoriaEntityAdapter, categoriaEntityAdapter);
	}

	@Bean
	UploadImagemDaSubcategoria uploadImagemDaSubcategoria() {
		return new UploadImagemDaSubcategoria(subcategoriaEntityAdapter, standardLogger);
	}
}
