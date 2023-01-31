package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.TotemEntity;

public interface TotemEntityDao extends JpaRepository<TotemEntity, Long> {

	Optional<TotemEntity> findByIdentificador(String identificador);

	Optional<TotemEntity> findByUsuarioId(Long usuarioId);
}
