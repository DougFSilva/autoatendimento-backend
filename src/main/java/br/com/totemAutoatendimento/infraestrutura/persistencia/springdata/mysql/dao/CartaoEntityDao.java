package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CartaoEntity;

@Repository
public interface CartaoEntityDao extends JpaRepository<CartaoEntity, String>{

	Optional<CartaoEntity> findByCodigo(String codigo);

}
