package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.FuncionarioEntity;

public interface FuncionarioEntityDao extends JpaRepository<FuncionarioEntity, Long>{

	Optional<FuncionarioEntity> findByMatricula(String matricula);

	Optional<FuncionarioEntity> findByCpf(String cpf);
}
