package br.com.totemAutoatendimento.dominio.comanda;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;

public interface ComandaRepository {

	Comanda criar(Comanda comanda);
	
	void remover(Comanda comanda);
	
	Comanda editar(Comanda comandaAtualizada);
	
	Optional<Comanda> buscar(Long id);

	Optional<Comanda> buscarPorCartao(String cartao, Boolean aberta);

	Optional<Comanda> buscarPorPedido(Pedido pedido);
	
	Page<Comanda> buscarPorCliente(Pageable paginacao, Cliente cliente);
	
	Page<Comanda> buscarPorData(Pageable paginacao, LocalDateTime dataInicial, LocalDateTime dataFinal);
	
	Page<Comanda> buscarPorTipoDePagamento(Pageable paginacao, TipoPagamento tipoPagamento);
	
	Page<Comanda> buscarAbertas(Pageable paginacao, Boolean aberta);
	
	Page<Comanda> buscarTodas(Pageable paginacao);
	
}
