package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ComandaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.PedidoEntity;

@Repository
public interface PedidoEntityDao extends JpaRepository<PedidoEntity, Long> {

    Page<PedidoEntity> findAllByDataBetween(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);

    Page<PedidoEntity> findAllByEntregue(Pageable paginacao, Boolean entregue);

	Page<PedidoEntity> findAllByComanda(Pageable paginacao, ComandaEntity entity);

}
