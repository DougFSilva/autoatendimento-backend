package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.avaliacao.Avaliacao;
import br.com.totemAutoatendimento.dominio.totem.Totem;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.AvaliacaoEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.TotemEntity;

@Service
public class AvaliacaoEntityConverter {

	@Autowired
	private TotemEntityConverter totemEntityConverter;
	
	public Avaliacao converterParaAvaliacao(AvaliacaoEntity entity) {
		Totem totem = totemEntityConverter.converterParaTotem(entity.getTotem());
		return new Avaliacao(
				entity.getId(),
				entity.getTimestamp(),
				totem,
				entity.getQualidadeDeMercadorias(),
				entity.getOpcoesDeMercadorias(),
				entity.getTempoDeAtendimento(),
				entity.getAmbiente(),
				entity.getExperienciaComAutoatendimento(),
				entity.getFuncionarios(),
				entity.getGerencia());
	}
	
	public AvaliacaoEntity converterParaAvaliacaoEntity(Avaliacao avaliacao) {
		TotemEntity totemEntity = totemEntityConverter.converterParaTotemEntity(avaliacao.getTotem());
		return new AvaliacaoEntity(
				avaliacao.getId(),
				avaliacao.getTimestamp(),
				totemEntity,
				avaliacao.getQualidadeDeMercadorias(),
				avaliacao.getOpcoesDeMercadorias(),
				avaliacao.getTempoDeAtendimento(),
				avaliacao.getAmbiente(),
				avaliacao.getExperienciaComAutoatendimento(),
				avaliacao.getFuncionarios(),
				avaliacao.getGerencia());
	}
}
