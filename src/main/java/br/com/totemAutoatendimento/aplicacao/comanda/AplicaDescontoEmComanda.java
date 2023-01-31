package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class AplicaDescontoEmComanda {

	private final ComandaRepository repository;

	private final StandardLogger logger;

	public AplicaDescontoEmComanda(ComandaRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	public DadosDeComanda aplicar(Long id, Float desconto, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Comanda> comanda = repository.buscarPeloId(id);
		if (comanda.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Comanda com id %d não encontrada!", id));
		}
		if (comanda.get().getDesconto() > 0) {
			throw new RegrasDeNegocioException("Só é permitido aplicar um desconto por comanda");
		}
		comanda.get().aplicarDesconto(desconto);
		Comanda comandaComDesconto = repository.salvar(comanda.get());
		logger.info(
				String.format("Aplicado desconto de %f em comanda com id %d!", 
						desconto, comandaComDesconto.getId()),usuarioAutenticado);
		return new DadosDeComanda(comandaComDesconto);
	}
}
