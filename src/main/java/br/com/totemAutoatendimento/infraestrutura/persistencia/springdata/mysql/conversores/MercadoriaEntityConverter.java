package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.MercadoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.SubcategoriaEntity;

@Service
public class MercadoriaEntityConverter {
	
	@Autowired
	private SubcategoriaEntityConverter subcategoriaEntityConverter;

	public Mercadoria converterParaMercadoria(MercadoriaEntity entity) {
		Subcategoria subcategoria = subcategoriaEntityConverter.converterParaSubcategoria(entity.getSubcategoria());
		return new Mercadoria(entity.getId(),
				entity.getCodigo(),
				subcategoria,
				entity.getDescricao(),
				entity.getPreco(),
				entity.getPromocao(),
				entity.getPrecoPromocional(),
				entity.getDisponivel(),
				entity.getImagem());
				
	}

	public MercadoriaEntity converterParaMercadoriaEntity(Mercadoria mercadoria) {
		SubcategoriaEntity subcategoriaEntity = subcategoriaEntityConverter.converterParaSubcategoriaEntity(mercadoria.getSubcategoria());
		return new MercadoriaEntity(mercadoria.getId(),
				mercadoria.getCodigo(),
				subcategoriaEntity,
				mercadoria.getDescricao(),
				mercadoria.getPreco(),
				mercadoria.getPromocao(),
				mercadoria.getPrecoPromocional(),
				mercadoria.getDisponivel(),
				mercadoria.getImagem());
				
	}
}
