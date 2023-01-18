package br.com.totemAutoatendimento.aplicacao.mercadoria;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.exception.RegrasDeNegocioException;
import br.com.totemAutoatendimento.dominio.mercadoria.Mercadoria;
import br.com.totemAutoatendimento.dominio.mercadoria.MercadoriaRepository;

public class VerificaDisponibilidadeDeMercadoria {

	private final MercadoriaRepository repository;

	public VerificaDisponibilidadeDeMercadoria(MercadoriaRepository repository) {
		this.repository = repository;
	}

	public Mercadoria verificar(String codigo) {
		Optional<Mercadoria> mercadoria = repository.buscarPeloCodigo(codigo);
		if (mercadoria.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Mercadoria com código %s não encontrada!", codigo));
		}
		if (!mercadoria.get().getDisponivel()) {
			throw new RegrasDeNegocioException("Não é possível realizar pedido, pois a mercadoria está indisponível!");
		}
		return mercadoria.get();
	}
}
