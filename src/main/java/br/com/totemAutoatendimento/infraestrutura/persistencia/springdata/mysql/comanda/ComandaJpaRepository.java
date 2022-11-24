package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.comanda;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;

public interface ComandaJpaRepository extends JpaRepository<Comanda, Long>{

}
