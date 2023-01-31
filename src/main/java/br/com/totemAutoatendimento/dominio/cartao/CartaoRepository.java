package br.com.totemAutoatendimento.dominio.cartao;

import java.util.List;
import java.util.Optional;

public interface CartaoRepository {

	Cartao salvar(Cartao cartao);
	
	void deletar(Cartao cartao);
	
	Optional<Cartao> buscarPeloCodigo(String codigo);
	
	List<Cartao> buscarTodos();
}
