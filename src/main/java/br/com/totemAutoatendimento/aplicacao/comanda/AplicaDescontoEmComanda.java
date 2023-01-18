package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class AplicaDescontoEmComanda {

	private final ComandaRepository repository;

	private final SystemLogger logger;

	public AplicaDescontoEmComanda(ComandaRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public DadosDeComanda aplicar(Long id, Float desconto, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
		Comanda comanda = buscaComandaPeloId.buscar(id);
		if (comanda.getDesconto() > 0) {
			throw new RegrasDeNegocioException("Só é permitido aplicar um desconto por comanda");
		}
		comanda.aplicarDesconto(desconto);
		Comanda comandaComDesconto = repository.editar(comanda);
		logger.info(
				String.format("Usuário %s - Aplicado desconto de %f em comanda com id %d!", 
						usuarioAutenticado.getRegistro(), desconto,comandaComDesconto.getId())
		);
		return new DadosDeComanda(comandaComDesconto);
	}
}
