package br.com.totemAutoatendimento.dominio.anotacao;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnotacaoRepository {

	Anotacao criar(Anotacao anotacao);
	
	void remover(Anotacao anotacao);
	
	Anotacao editar(Anotacao anotacaoAtualizada);
	
	Anotacao buscarPeloId(Long id);
	
	Page<Anotacao> buscarPorData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);
	
	Page<Anotacao> buscarTodas(Pageable paginacao);
}
