package br.com.totemAutoatendimento.aplicacao.mercadoria;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class BuscaMercadoriaPeloId {

	private final MercadoriaRepository repository;

	public BuscaMercadoriaPeloId(MercadoriaRepository repository) {
		this.repository = repository;
	}

	public Mercadoria buscar(Long id) {
		return repository.buscarPeloId(id)
				.orElseThrow(() -> new ObjetoNaoEncontradoException(String.format("Mercadoria com id %d não encontrada!", id)));
	}
}
