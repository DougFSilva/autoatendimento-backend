package br.com.totemAutoatendimento.dominio.avaliacao;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvaliacaoRepository {

	Avaliacao salvar(Avaliacao avaliacao);
	
	void deletar(Avaliacao avaliacao);
	
	Optional<Avaliacao> buscarPeloId(Long id);
	
	Optional<Avaliacao> buscarUltimaPeloTotem(Long totemId);
	
	Page<Avaliacao> buscarTodasPelaData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal);
	
	Page<Avaliacao> buscarTodas(Pageable paginacao);
}
