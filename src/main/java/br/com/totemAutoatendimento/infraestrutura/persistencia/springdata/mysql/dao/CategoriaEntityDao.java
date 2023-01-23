package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CategoriaEntity;

public interface CategoriaEntityDao extends JpaRepository<CategoriaEntity, Long>{

	Optional<CategoriaEntity> findByNome(String nome);

}
