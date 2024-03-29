package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CategoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.SubcategoriaEntity;

public interface SubcategoriaEntityDao extends JpaRepository<SubcategoriaEntity, Long> {

    Optional<SubcategoriaEntity> findByNome(String nome);

	List<SubcategoriaEntity> findAllByCategoria(CategoriaEntity entity);
    
}
