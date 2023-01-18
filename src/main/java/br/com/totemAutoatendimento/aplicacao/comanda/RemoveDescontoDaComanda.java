package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class RemoveDescontoDaComanda {

	private final ComandaRepository repository;

	private final SystemLogger logger;

	public RemoveDescontoDaComanda(ComandaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public DadosDeComanda remover(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		BuscaComandaPeloId buscarComandaPeloId = new BuscaComandaPeloId(repository);
		Comanda comanda = buscarComandaPeloId.buscar(id);
		comanda.removerDesconto();
		Comanda comandaComDescontoRemovido = repository.editar(comanda);
		logger.info(
				String.format("Usuário %s - Desconto removida de comanda com id %d!", 
						usuarioAutenticado.getRegistro(), comandaComDescontoRemovido.getId())
		);
		return new DadosDeComanda(comandaComDescontoRemovido);
	}
}
