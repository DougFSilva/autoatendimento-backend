package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.DeletaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.UploadImagemDaCategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.CategoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.SubcategoriaEntityAdapter;

@Configuration
public class CategoriaBeanConfiguration {

	@Autowired
	private CategoriaEntityAdapter categoriaEntityAdapter;

	@Autowired
	private SubcategoriaEntityAdapter subcategoriaEntityAdapter;
	
	@Autowired
	private StandardLogger standardLogger;

	@Bean
	CriaCategoria criaCategoria() {
		return new CriaCategoria(categoriaEntityAdapter, standardLogger);
	}

	@Bean
	DeletaCategoria removeCategoria() {
		return new DeletaCategoria(categoriaEntityAdapter, subcategoriaEntityAdapter, standardLogger);
	}

	@Bean
	EditaCategoria editaCategoria() {
		return new EditaCategoria(categoriaEntityAdapter, standardLogger);
	}

	@Bean
	BuscaTodasCategorias buscarCategorias() {
		return new BuscaTodasCategorias(categoriaEntityAdapter);
	}

	@Bean
	UploadImagemDaCategoria uploadImagemDaCategoria() {
		return new UploadImagemDaCategoria(categoriaEntityAdapter, standardLogger);
	}

}
