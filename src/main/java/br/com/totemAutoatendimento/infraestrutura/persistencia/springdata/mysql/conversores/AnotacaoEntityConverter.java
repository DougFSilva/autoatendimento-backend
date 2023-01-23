package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.anotacao.Anotacao;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.AnotacaoEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Service
public class AnotacaoEntityConverter {
	
	@Autowired
	private UsuarioEntityConverter usuarioEntityConverter;

	public Anotacao converterParaAnotacao(AnotacaoEntity entity) {
		Usuario usuario = usuarioEntityConverter.converterParaUsuario(entity.getRegistrador());
		return new Anotacao(entity.getId(), entity.getTimestamp(), usuario, entity.getDescricao(),
				entity.getNivelDeImportancia());
	}
	
	public AnotacaoEntity converterParaAnotacaoEntity(Anotacao anotacao) {
		UsuarioEntity usuarioEntity = usuarioEntityConverter.converterParaUsuarioEntity(anotacao.getRegistrador());
		return new AnotacaoEntity(anotacao.getId(), anotacao.getTimestamp(), usuarioEntity, anotacao.getDescricao(),
				anotacao.getNivelDeImportancia());
	}
}
