package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaTodasCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.RemoveCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.UploadImagemDaCategoria;
import br.com.totemAutoatendimento.infraestrutura.logger.LoggerAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.CategoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.SubcategoriaEntityAdapter;

@Configuration
public class CategoriaBeanConfiguration {

	@Autowired
	private CategoriaEntityAdapter categoriaEntityAdapter;

	@Autowired
	private SubcategoriaEntityAdapter subcategoriaEntityAdapter;
	
	@Autowired
	private LoggerAdapter loggerAdapter;

	@Bean
	public CriaCategoria criaCategoria() {
		return new CriaCategoria(categoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	public RemoveCategoria removeCategoria() {
		return new RemoveCategoria(categoriaEntityAdapter, subcategoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	public EditaCategoria editaCategoria() {
		return new EditaCategoria(categoriaEntityAdapter, loggerAdapter);
	}

	@Bean
	public BuscaTodasCategorias buscarCategorias() {
		return new BuscaTodasCategorias(categoriaEntityAdapter);
	}

	@Bean
	public UploadImagemDaCategoria uploadImagemDaCategoria() {
		return new UploadImagemDaCategoria(categoriaEntityAdapter, loggerAdapter);
	}

}
