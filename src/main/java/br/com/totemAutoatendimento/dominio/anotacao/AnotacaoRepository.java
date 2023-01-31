package br.com.totemAutoatendimento.dominio.anotacao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnotacaoRepository {

	Anotacao salvar(Anotacao anotacao);
	
	void deletar(Anotacao anotacao);
	
	Optional<Anotacao> buscarPeloId(Long id);
	
	Page<Anotacao> buscarPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);
	
	Page<Anotacao> buscarTodas(Pageable paginacao);
}
