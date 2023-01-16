package br.com.totemAutoatendimento.infraestrutura.config.Bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaCategorias;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.CriaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.EditaCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.RemoveCategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.UploadImagemDaCategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.CategoriaEntityAdapter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores.SubcategoriaEntityAdapter;

@Configuration
public class CategoriaBeanConfiguration {

	@Autowired
	private CategoriaEntityAdapter categoriaEntityAdapter;

	@Autowired
	private SubcategoriaEntityAdapter subcategoriaEntityAdapter;

	@Bean
	public CriaCategoria criaCategoria() {
		return new CriaCategoria(categoriaEntityAdapter);
	}

	@Bean
	public RemoveCategoria removeCategoria() {
		return new RemoveCategoria(categoriaEntityAdapter, subcategoriaEntityAdapter);
	}

	@Bean
	public EditaCategoria editaCategoria() {
		return new EditaCategoria(categoriaEntityAdapter);
	}

	@Bean
	public BuscaCategorias buscarCategorias() {
		return new BuscaCategorias(categoriaEntityAdapter);
	}

	@Bean
	public UploadImagemDaCategoria uploadImagemDaCategoria() {
		return new UploadImagemDaCategoria(categoriaEntityAdapter);
	}

}
