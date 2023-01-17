package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class AplicaDescontoEmComanda {

	private final ComandaRepository repository;

	public AplicaDescontoEmComanda(ComandaRepository repository) {
		this.repository = repository;
	}

	public DadosDeComanda aplicar(Long id, Float desconto) {
		BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
		Comanda comanda = buscaComandaPeloId.buscar(id);
		if(comanda.getDesconto() > 0) {
			throw new RegrasDeNegocioException("Só é permitido aplicar um desconto por comanda");
		}
		comanda.aplicarDesconto(desconto);
		return new DadosDeComanda(repository.editar(comanda));
	}
}
