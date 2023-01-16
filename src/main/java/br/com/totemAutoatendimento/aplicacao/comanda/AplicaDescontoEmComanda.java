package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.comanda.dto.DadosDeComanda;
import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class AplicaDescontoEmComanda {

	private final ComandaRepository repository;

	public AplicaDescontoEmComanda(ComandaRepository repository) {
		this.repository = repository;
	}

	public DadosDeComanda aplicar(Long id, Float desconto) {
		BuscaComandaPeloId buscaComandaPeloId = new BuscaComandaPeloId(repository);
		Comanda comanda = buscaComandaPeloId.buscar(id);
		comanda.aplicarDesconto(desconto);
		return new DadosDeComanda(repository.editar(comanda));
	}
}
