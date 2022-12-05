package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoriaEntityJpaRepository extends JpaRepository<SubcategoriaEntity, Long> {

    Optional<SubcategoriaEntity> findByNome(String nome);
    
}
