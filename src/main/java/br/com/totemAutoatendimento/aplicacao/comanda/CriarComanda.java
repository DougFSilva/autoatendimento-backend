package br.com.totemAutoatendimento.aplicacao.comanda;

import br.com.totemAutoatendimento.dominio.comanda.Comanda;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.comanda.DadosCriarComanda;

public class CriarComanda {
	
	private ComandaRepository repository;
	
	public CriarComanda(ComandaRepository repository) {
		this.repository = repository;
	}
	
	public Comanda executar(DadosCriarComanda dados) {
		return null;
	}

}
