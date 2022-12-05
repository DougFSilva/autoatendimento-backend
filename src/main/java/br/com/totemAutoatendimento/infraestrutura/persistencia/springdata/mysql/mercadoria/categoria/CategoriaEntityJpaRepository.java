package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaEntityJpaRepository extends JpaRepository<CategoriaEntity, Long>{

	Optional<CategoriaEntity> findByNome(String nome);

}
