package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CategoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.SubcategoriaEntity;

@Service
public class SubcategoriaEntityConverter {
	
	@Autowired
	private CategoriaEntityConverter categoriaEntityConverter;

	public Subcategoria converterParaSubcategoria(SubcategoriaEntity entity) {
		Categoria categoria = categoriaEntityConverter.converterParaCategoria(entity.getCategoria());
		return new Subcategoria(entity.getId(), categoria, entity.getNome(), entity.getImagem());
	}

	public SubcategoriaEntity converterParaSubcategoriaEntity(Subcategoria subcategoria) {
		CategoriaEntity categoriaEntity = categoriaEntityConverter.converterParaCategoriaEntity(subcategoria.getCategoria());
		return new SubcategoriaEntity(subcategoria.getId(), categoriaEntity, subcategoria.getNome(), subcategoria.getImagem());
	}

}
