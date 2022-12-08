package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.categoria.CategoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.mercadoria.subcategoria.SubcategoriaEntity;

public interface MercadoriaEntityJpaRepository extends JpaRepository<MercadoriaEntity, Long>{

    Page<MercadoriaEntity> findAllByCategoria(Pageable paginacao, CategoriaEntity categoriaEntity);

    Page<MercadoriaEntity> findAllBySubcategoria(Pageable paginacao, SubcategoriaEntity subcategoriaEntity);

    Page<MercadoriaEntity> findAllByPromocao(Pageable paginacao, Boolean promocao);
    
}
