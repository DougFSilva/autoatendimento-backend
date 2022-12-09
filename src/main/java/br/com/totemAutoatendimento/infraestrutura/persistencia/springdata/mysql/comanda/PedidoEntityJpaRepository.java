package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoEntityJpaRepository extends JpaRepository<PedidoEntity, Long> {
    
}
