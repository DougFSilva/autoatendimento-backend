package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.TipoPagamento;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class ReabreComanda {

	private final ComandaRepository repository;

	private final SystemLogger logger;

	public ReabreComanda(ComandaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeComanda reabrir(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
		Comanda comanda = buscaComandaPeloId.buscar(id);
		comanda.setAberta(true);
		comanda.setTipoPagamento(TipoPagamento.NAO_PAGO);
		comanda.removerDesconto();
		comanda.setDesconto(0f);
		comanda.setFechamento(null);
		Comanda comandaReaberta = repository.editar(comanda);
		logger.info(
				String.format("Usuário %s - Comanda com id %d reaberta!", usuarioAutenticado.getRegistro(), comandaReaberta.getId())
		);
		return new DadosDeComanda(comandaReaberta);
	}

}
