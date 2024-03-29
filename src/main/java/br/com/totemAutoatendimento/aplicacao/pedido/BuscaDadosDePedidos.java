package br.com.totemAutoatendimento.aplicacao.pedido;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.pedido.dto.DadosDePedido;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pedido.Pedido;
import br.com.totemAutoatendimento.dominio.pedido.PedidoRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDePedidos {

	private final PedidoRepository repository;
	
	private final ComandaRepository comandaRepository;

	public BuscaDadosDePedidos(PedidoRepository repository, ComandaRepository comandaRepository) {
		this.repository = repository;
		this.comandaRepository = comandaRepository;
	}

	public DadosDePedido buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Pedido> pedido = repository.buscarPeloId(id);
		if(pedido.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Pedido com id %d não encontrado", id));
		}
		return new DadosDePedido(pedido.get());
	}

	public List<DadosDePedido> buscarPelaComanda(Long comandaId, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		Optional<Comanda> comanda = comandaRepository.buscarPeloId(comandaId);
    	if(comanda.isEmpty()) {
    		throw new ObjetoNaoEncontradoException(String.format("Comanda com id %d não encontrada!", comandaId));
    	}
    	return repository.buscarPelaComanda(comanda.get()).stream().map(DadosDePedido::new).toList();
	}

	public Page<DadosDePedido> buscarEntregues(Pageable paginacao, Boolean entregue, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarEntregue(paginacao, entregue).map(DadosDePedido::new);
	}

	public Page<DadosDePedido> buscarPorData(Pageable paginacao, LocalDate dataInicial, LocalDate dataFinal, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarPelaData(paginacao, dataInicial, dataFinal).map(DadosDePedido::new);
	}

	public Page<DadosDePedido> buscarTodos(Pageable paginacao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return repository.buscarTodos(paginacao).map(DadosDePedido::new);
	}
}
