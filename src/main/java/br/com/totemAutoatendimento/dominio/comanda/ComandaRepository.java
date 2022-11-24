package br.com.totemAutoatendimento.dominio.comanda;

import java.time.LocalDateTime;
import java.util.List;

public interface ComandaRepository {

	Comanda criar(Comanda comanda);
	
	void remover(Long id);
	
	Comanda editar(Long id, Comanda comandaAtualizada);
	
	Comanda buscar(Long id);
	
	List<Comanda> buscarPorCliente(Long id);
	
	List<Comanda> buscarPorData(LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	List<Comanda> buscarPorTipoDePagamento(TipoPagamento tipoPagamento);
	
	List<Comanda> buscarAbertas();
	
	List<Comanda> buscarTodas();
	
}
