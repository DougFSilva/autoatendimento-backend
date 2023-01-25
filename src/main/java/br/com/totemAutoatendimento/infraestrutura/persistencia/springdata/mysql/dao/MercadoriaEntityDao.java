package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.dao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasDeMaiorFaturamento;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasMaisVendidas;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.MercadoriaEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.SubcategoriaEntity;

public interface MercadoriaEntityDao extends JpaRepository<MercadoriaEntity, Long> {

	Page<MercadoriaEntity> findAllBySubcategoria(Pageable paginacao, SubcategoriaEntity subcategoriaEntity);

	Page<MercadoriaEntity> findAllByPromocao(Pageable paginacao, Boolean promocao);

	Optional<MercadoriaEntity> findByCodigo(String codigo);

	@Query("SELECT new br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasMaisVendidas("
			+ "pedido.mercadoria.codigo, "
			+ "pedido.mercadoria.subcategoria.nome, "
			+ "pedido.mercadoria.descricao, "
			+ "SUM(pedido.quantidade) AS quantidade) "
			+ "FROM PedidoEntity pedido "
			+ "WHERE pedido.data BETWEEN :dataInicial AND :dataFinal "
			+ "GROUP BY pedido.mercadoria.id "
			+ "ORDER BY quantidade DESC")
	Page<RelatorioMercadoriasMaisVendidas> buscarMaisVendidasPelaData(Pageable paginacao, 
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate DataFinal);
	
	@Query("SELECT new br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasDeMaiorFaturamento("
			+ "pedido.mercadoria.codigo, "
			+ "pedido.mercadoria.subcategoria.nome, "
			+ "pedido.mercadoria.descricao, "
			+ "SUM(pedido.valor) AS valor) "
			+ "FROM PedidoEntity pedido "
			+ "WHERE pedido.data BETWEEN :dataInicial AND :dataFinal "
			+ "GROUP BY pedido.mercadoria.id "
			+ "ORDER BY valor DESC")
	Page<RelatorioMercadoriasDeMaiorFaturamento> buscarComMaiorFaturamentoPelaData(Pageable paginacao, 
			@Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate DataFinal);

}
