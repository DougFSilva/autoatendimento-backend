package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CartaoEntity;

public interface CartaoEntityDao extends JpaRepository<CartaoEntity, String>{

	Optional<CartaoEntity> findByCodigo(String codigo);

}
