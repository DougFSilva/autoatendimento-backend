package br.com.totemAutoatendimento.dominio.comanda;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;

public interface ComandaRepository {

	Comanda salvar(Comanda comanda);

	void deletar(Comanda comanda);

	Optional<Comanda> buscarPeloId(Long id);

	Optional<Comanda> buscarPeloCartao(String codigoCartao, Boolean aberta);

	Page<Comanda> buscarPeloCliente(Pageable paginacao, Cliente cliente);

	Page<Comanda> buscarPelaData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal);

	Page<Comanda> buscarPeloTipoDePagamento(Pageable paginacao, TipoPagamento tipoPagamento);

	Page<Comanda> buscarAbertas(Pageable paginacao, Boolean aberta);

	Page<Comanda> buscarTodas(Pageable paginacao);

}
