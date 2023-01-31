package br.com.totemAutoatendimento.dominio.funcionario;

import java.util.List;
import java.util.Optional;

public interface FuncionarioRepository {

	Funcionario salvar(Funcionario funcionario);
	
	void deletar(Funcionario funcionario);
	
	Optional<Funcionario> buscarPeloId(Long id);
	
	Optional<Funcionario> buscarPelaMatricula(String matricula);
	
	Optional<Funcionario> buscarPeloCpf(String cpf);
	
	Optional<Funcionario> buscarPeloUsuario(Long usuarioId);
	
	List<Funcionario> buscarTodos();
}
