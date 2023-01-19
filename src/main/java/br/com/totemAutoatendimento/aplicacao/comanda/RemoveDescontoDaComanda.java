package br.com.totemAutoatendimento.aplicacao.comanda;

import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
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
		Optional<Comanda> comanda = repository.buscarPeloId(id);
    	if(comanda.isEmpty()) {
    		throw new ObjetoNaoEncontradoException(String.format("Comanda com id %d não encontrada!", id));
    	}
		comanda.get().removerDesconto();
		Comanda comandaComDescontoRemovido = repository.editar(comanda.get());
		logger.info(
				String.format("Usuário %s - Desconto removida de comanda com id %d!", 
						usuarioAutenticado.getRegistro(), comandaComDescontoRemovido.getId())
		);
		return new DadosDeComanda(comandaComDescontoRemovido);
	}
}
