package br.com.totemAutoatendimento.dominio.mercadoria;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasDeMaiorFaturamento;
import br.com.totemAutoatendimento.dominio.mercadoria.relatorio.RelatorioMercadoriasMaisVendidas;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;

public interface MercadoriaRepository {

	Mercadoria salvar(Mercadoria mercadoria);

	void deletar(Mercadoria mercadoria);

	Optional<Mercadoria> buscarPeloId(Long id);

	Optional<Mercadoria> buscarPeloCodigo(String codigo);

	Page<Mercadoria> buscarPelaSubcategoria(Pageable paginacao, Subcategoria subcategoria);

	Page<Mercadoria> buscarEmPromocao(Pageable paginacao, Boolean promocao);

	Page<Mercadoria> buscarTodas(Pageable paginacao);
	
	Page<RelatorioMercadoriasMaisVendidas> buscarMaisVendidasPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);
	
	Page<RelatorioMercadoriasDeMaiorFaturamento> buscarComMaiorFaturamentoPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);

}
