package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pedido;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoEntityJpaRepository extends JpaRepository<PedidoEntity, Long> {

    Page<PedidoEntity> findAllByDataBetween(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);

    Page<PedidoEntity> findAllByEntregue(Pageable paginacao, Boolean entregue);

}
