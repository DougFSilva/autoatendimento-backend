package br.com.totemAutoatendimento.aplicacao.cliente;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosDeCliente;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

@PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
public class BuscaDadosDeClientes {

	private final ClienteRepository repository;

	public BuscaDadosDeClientes(ClienteRepository repository) {
		this.repository = repository;
	}
	
	public DadosDeCliente buscarPeloId(Long id) {
		BuscaClientePeloId buscaClientePeloId = new BuscaClientePeloId(repository);
		return new DadosDeCliente(buscaClientePeloId.buscar(id));
	}
	
    public DadosDeCliente buscarPeloCpf(String cpf) {
        Optional<Cliente> cliente = repository.buscarClientePorCpf(cpf);
        if (cliente.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Cliente com cpf :" + cpf + " não encontrado!");
        }
        return new DadosDeCliente(cliente.get());
    }
    
	public Page<DadosDeCliente> buscarPelaCidade(Pageable paginacao, String cidade) {
		return this.repository.buscarPorCidade(paginacao, cidade).map(DadosDeCliente::new);
	}
	
	public Page<DadosDeCliente> buscarTodos(Pageable paginacao) {
		return this.repository.buscarTodos(paginacao).map(DadosDeCliente::new);
	}
}
