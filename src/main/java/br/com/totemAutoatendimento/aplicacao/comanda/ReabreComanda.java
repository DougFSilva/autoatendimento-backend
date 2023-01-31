package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class ReabreComanda {

	private final ComandaRepository repository;

	private final StandardLogger logger;

	public ReabreComanda(ComandaRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeComanda reabrir(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Comanda> comanda = repository.buscarPeloId(id);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Comanda com id %d não encontrada!", id));
		}
		comanda.get().setAberta(true);
		comanda.get().setTipoPagamento(TipoPagamento.NAO_PAGO);
		comanda.get().removerDesconto();
		comanda.get().setDesconto(0f);
		comanda.get().setFechamento(null);
		Comanda comandaReaberta = repository.salvar(comanda.get());
		logger.info(String.format("Comanda com id %d reaberta!", comandaReaberta.getId()), usuarioAutenticado);
		return new DadosDeComanda(comandaReaberta);
	}

}
