package br.com.totemAutoatendimento.aplicacao.funcionario;

import java.util.List;
import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.funcionario.dto.DadosDeFuncionario;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import br.com.totemAutoatendimento.dominio.funcionario.FuncionarioRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDeFuncionario {

	private final FuncionarioRepository repository;
	
	public BuscaDadosDeFuncionario(FuncionarioRepository repository) {
		this.repository = repository;
	}
	
	public DadosDeFuncionario buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Funcionario> funcionario = repository.buscarPeloId(id);
		if(funcionario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Funcionário com id %d não encontrado!", id));
		}
		return new DadosDeFuncionario(funcionario.get());
	}
	
	public DadosDeFuncionario buscarPelaMatricuka(String matricula, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		Optional<Funcionario> funcionario = repository.buscarPelaMatricula(matricula);
		if(funcionario.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Funcionário com matrícula %s não encontrado!", matricula));
		}
		return new DadosDeFuncionario(funcionario.get());
	}
	
	public List<DadosDeFuncionario> buscarTodos(Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
		return repository.buscarTodos().stream().map(DadosDeFuncionario::new).toList();
	}
	
	
}
