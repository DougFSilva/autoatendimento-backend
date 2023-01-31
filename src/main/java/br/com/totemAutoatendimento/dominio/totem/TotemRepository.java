package br.com.totemAutoatendimento.dominio.totem;

import java.util.List;
import java.util.Optional;

public interface TotemRepository {

	Totem salvar(Totem totem);
	
	void deletar(Totem totem);
	
	Optional<Totem> buscarPeloId(Long id);
	
	Optional<Totem> buscarPeloIdentificador(String identificador);
	
	List<Totem> buscarTodos();
}
