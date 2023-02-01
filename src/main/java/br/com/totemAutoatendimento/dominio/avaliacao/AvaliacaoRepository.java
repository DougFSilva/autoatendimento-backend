package br.com.totemAutoatendimento.dominio.avaliacao;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AvaliacaoRepository {

	Avaliacao salvar(Avaliacao avaliacao);
	
	void deletar(Avaliacao avaliacao);
	
	Optional<Avaliacao> buscarPeloId(Long id);
	
	Optional<Avaliacao> buscarUltimaPeloTotem(Long totemId);
	
	Page<Avaliacao> buscarTodasPelaData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	Page<Avaliacao> buscarTodas(Pageable paginacao);
}
