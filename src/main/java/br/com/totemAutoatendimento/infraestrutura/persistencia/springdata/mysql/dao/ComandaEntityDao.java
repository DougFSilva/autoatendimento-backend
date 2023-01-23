package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.CartaoEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ClienteEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.ComandaEntity;

public interface ComandaEntityDao extends JpaRepository<ComandaEntity, Long>{

    Optional<ComandaEntity> findByCartaoAndAberta(CartaoEntity cartao, Boolean aberta);

    Page<ComandaEntity> findAllByAberta(Pageable paginacao, Boolean aberta);

    Page<ComandaEntity> findAllByCliente(Pageable paginacao, ClienteEntity cliente);

    Page<ComandaEntity> findAllByAberturaBetween(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal);

    Page<ComandaEntity> findAllByTipoPagamento(Pageable paginacao, TipoPagamento tipoPagamento);

}
