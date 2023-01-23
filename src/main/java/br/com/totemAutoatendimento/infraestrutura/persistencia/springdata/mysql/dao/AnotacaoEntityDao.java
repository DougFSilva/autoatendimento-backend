package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.AnotacaoEntity;

public interface AnotacaoEntityDao extends JpaRepository<AnotacaoEntity, Long> {

	Page<AnotacaoEntity> findAllByTimestampBetween(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal);

}
