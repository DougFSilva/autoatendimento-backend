package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.adaptadores;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import br.com.totemAutoatendimento.dominio.funcionario.FuncionarioRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores.FuncionarioEntityConverter;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao.FuncionarioEntityDao;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.FuncionarioEntity;

@Repository
public class FuncionarioEntityAdapter implements FuncionarioRepository{

	@Autowired
	private FuncionarioEntityDao dao;
	
	@Autowired
	private FuncionarioEntityConverter funcionarioEntityConverter;

	@Override
	public Funcionario salvar(Funcionario funcionario) {
		FuncionarioEntity entity = funcionarioEntityConverter.converterParaFuncionarioEntity(funcionario);
		return funcionarioEntityConverter.converterParaFuncionario(dao.save(entity));
	}

	@Override
	public void deletar(Funcionario funcionario) {
		FuncionarioEntity entity = funcionarioEntityConverter.converterParaFuncionarioEntity(funcionario);
		dao.delete(entity);
	}

	@Override
	public Optional<Funcionario> buscarPeloId(Long id) {
		Optional<FuncionarioEntity> entity = dao.findById(id);
		if(entity.isPresent()) {
			return Optional.of(funcionarioEntityConverter.converterParaFuncionario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Funcionario> buscarPelaMatricula(String matricula) {
		Optional<FuncionarioEntity> entity = dao.findByMatricula(matricula);
		if(entity.isPresent()) {
			return Optional.of(funcionarioEntityConverter.converterParaFuncionario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<Funcionario> buscarPeloCpf(String cpf) {
		Optional<FuncionarioEntity> entity = dao.findByCpf(cpf);
		if(entity.isPresent()) {
			return Optional.of(funcionarioEntityConverter.converterParaFuncionario(entity.get()));
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<Funcionario> buscarPeloUsuario(Long usuarioId) {
		Optional<FuncionarioEntity> entity = dao.findByUsuarioId(usuarioId);
		if(entity.isPresent()) {
			return Optional.of(funcionarioEntityConverter.converterParaFuncionario(entity.get()));
		}
		return Optional.empty();
	}

	@Override
	public List<Funcionario> buscarTodos() {
		return dao.findAll().stream().map(funcionarioEntityConverter::converterParaFuncionario).toList();
	}

	
}
