package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

public interface UsuarioEntityDao extends JpaRepository<UsuarioEntity, Long> {

	Optional<UsuarioEntity> findByUsername(String username);

}
