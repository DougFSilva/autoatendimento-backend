package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.dominio.totem.TotemRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.TotemEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.TotemEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.TotemEntity;

@Repository
public class TotemEntityAdapter implements TotemRepository {

	@Autowired
	private TotemEntityDao dao;

	@Autowired
	private TotemEntityConverter totemEntityConverter;

	@Override
	public Totem salvar(Totem totem) {
		TotemEntity entity = totemEntityConverter.converterParaTotemEntity(totem);	
		return totemEntityConverter.converterParaTotem(dao.save(entity));
	}

	@Override
	public void deletar(Totem totem) {
		TotemEntity entity = totemEntityConverter.converterParaTotemEntity(totem);	
		dao.delete(entity);
	}

	@Override
	public Optional<Totem> buscarPeloId(Long id) {
		Optional<TotemEntity> entity = dao.findById(id);
		if(entity.isPresent()) {
			return Optional.of(totemEntityConverter.converterParaTotem(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Totem> buscarPeloIdentificador(String identificador) {
		Optional<TotemEntity> entity = dao.findByIdentificador(identificador);
		if(entity.isPresent()) {
			return Optional.of(totemEntityConverter.converterParaTotem(entity.get()));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<Totem> buscarPeloUsuario(Long usuarioId) {
		Optional<TotemEntity> entity = dao.findByUsuarioId(usuarioId);
		if(entity.isPresent()) {
			return Optional.of(totemEntityConverter.converterParaTotem(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Totem> buscarTodos() {
		return dao.findAll().stream().map(totemEntityConverter::converterParaTotem).toList();
	}


}
