package br.com.totemAutoatendimento.dominio.mercadoria.categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

	Categoria salvar(Categoria categoria);
	
	void deletar(Categoria categoria);
	
	Optional<Categoria> buscarPeloId(Long id);
	
	Optional<Categoria> buscarPorNome(String nome);
	
	List<Categoria> buscarTodas();
	
}
