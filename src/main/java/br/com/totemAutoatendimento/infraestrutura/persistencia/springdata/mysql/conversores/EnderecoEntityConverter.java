package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.EnderecoEntity;

@Service
public class EnderecoEntityConverter {

	public Endereco converterParaEndereco(EnderecoEntity entity) {
		return new Endereco(entity.getId(), 
				entity.getEstado(), 
				entity.getCidade(), 
				entity.getBairro(), 
				entity.getRua(),
				entity.getNumero());
	}

	public EnderecoEntity converterParaEnderecoEntity(Endereco endereco) {
		return new EnderecoEntity(endereco.getId(), 
				endereco.getEstado(),
				endereco.getCidade(), 
				endereco.getBairro(),
				endereco.getRua(), 
				endereco.getNumero());
	}
}
