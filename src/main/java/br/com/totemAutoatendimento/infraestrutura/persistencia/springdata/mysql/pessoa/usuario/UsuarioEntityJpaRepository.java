package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioEntityJpaRepository extends JpaRepository<UsuarioEntity, Long>{

}
