package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CategoriaEntity;

@Service
public class CategoriaEntityConverter {

	public Categoria converterParaCategoria(CategoriaEntity entity) {
		return new Categoria(entity.getId(), entity.getNome(), entity.getImagem());
	}

	public CategoriaEntity converterParaCategoriaEntity(Categoria categoria) {
		return new CategoriaEntity(categoria.getId(), categoria.getNome(), categoria.getImagem());
	}
}
