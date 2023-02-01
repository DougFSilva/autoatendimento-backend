package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.AvaliacaoEntity;

public interface AvaliacaoEntityDao extends JpaRepository<AvaliacaoEntity, Long> {

	Optional<AvaliacaoEntity> findFirstByTotemIdOrderByTimestampDesc(Long totemId);


	Page<AvaliacaoEntity> findAllByTimestampBetween(Pageable paginacao, LocalDateTime dataInicial,
			LocalDateTime dataFinal);

}
