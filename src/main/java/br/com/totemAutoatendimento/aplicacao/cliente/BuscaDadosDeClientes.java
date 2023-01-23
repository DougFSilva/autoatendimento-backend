package br.com.totemAutoatendimento.aplicacao.cliente;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosDeCliente;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaDadosDeClientes {

	private final ClienteRepository repository;

	public BuscaDadosDeClientes(ClienteRepository repository) {
		this.repository = repository;
	}

	public DadosDeCliente buscarPeloId(Long id, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Cliente> cliente = repository.buscarPeloId(id);
		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cliente com id %d não encontrado!", id));
		}
		return new DadosDeCliente(cliente.get());
	}

	public DadosDeCliente buscarPeloCpf(String cpf, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		Optional<Cliente> cliente = repository.buscarClientePorCpf(cpf);
		if (cliente.isEmpty()) {
			throw new ObjetoNaoEncontradoException(String.format("Cliente com cpf %s não encontrado!", cpf));
		}
		return new DadosDeCliente(cliente.get());
	}

	public Page<DadosDeCliente> buscarPelaCidade(Pageable paginacao, String cidade, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return this.repository.buscarPorCidade(paginacao, cidade).map(DadosDeCliente::new);
	}

	public Page<DadosDeCliente> buscarTodos(Pageable paginacao, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		return this.repository.buscarTodos(paginacao).map(DadosDeCliente::new);
	}

}
