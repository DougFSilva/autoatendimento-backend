package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.MercadoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.SubcategoriaEntity;

public interface MercadoriaEntityDao extends JpaRepository<MercadoriaEntity, Long> {

	Page<MercadoriaEntity> findAllBySubcategoria(Pageable paginacao, SubcategoriaEntity subcategoriaEntity);

	Page<MercadoriaEntity> findAllByPromocao(Pageable paginacao, Boolean promocao);

	Optional<MercadoriaEntity> findByCodigo(String codigo);

}
