package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.totemAutoatendimento.aplicacao.mercadoria.dto.RelatorioMercadoriasMaisVendidas;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.MercadoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.SubcategoriaEntity;

public interface MercadoriaEntityDao extends JpaRepository<MercadoriaEntity, Long> {

	Page<MercadoriaEntity> findAllBySubcategoria(Pageable paginacao, SubcategoriaEntity subcategoriaEntity);

	Page<MercadoriaEntity> findAllByPromocao(Pageable paginacao, Boolean promocao);

	Optional<MercadoriaEntity> findByCodigo(String codigo);

	@Query(value = "SELECT new br.com.totemAutoatendimento.aplicacao.mercadoria.dto.RelatorioMercadoriasMaisVendidas"
			+ "(pedido.mercadoria.codigo, "
			+ "pedido.mercadoria.subcategoria.nome, "
			+ "pedido.mercadoria.descricao, "
			+ "SUM(pedido.quantidade) AS quantidade) "
			+ "FROM PedidoEntity pedido "
			+ "WHERE pedido.data BETWEEN :dataInicial AND :dataFinal "
			+ "GROUP BY pedido.mercadoria.id")
	Page<RelatorioMercadoriasMaisVendidas> buscarMaisVendidasPelaData(Pageable paginacao, 
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate DataFinal);

}
