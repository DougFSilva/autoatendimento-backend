package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente.ClienteEntity;

@Repository
public interface ComandaEntityJpaRepository extends JpaRepository<ComandaEntity, Long>{

    Optional<ComandaEntity> findByCartaoAndAberta(String cartao, Boolean aberta);

    Page<ComandaEntity> findAllByAberta(Pageable paginacao, Boolean aberta);

    Page<ComandaEntity> findAllByCliente(Pageable paginacao, ClienteEntity cliente);

    Page<ComandaEntity> findAllByAberturaBetween(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal);

    Page<ComandaEntity> findAllByTipoPagamento(Pageable paginacao, TipoPagamento tipoPagamento);
}
