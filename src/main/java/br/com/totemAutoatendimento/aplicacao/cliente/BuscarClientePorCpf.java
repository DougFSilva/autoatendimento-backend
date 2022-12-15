package br.com.totemAutoatendimento.aplicacao.cliente;

import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;

public class BuscarClientePorCpf {

    private ClienteRepository repository;

    public BuscarClientePorCpf(ClienteRepository repository) {
        this.repository = repository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public DadosDeCliente executar(String cpf) {
        Optional<Cliente> cliente = repository.buscarClientePorCpf(cpf);
        if (cliente.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Cliente com cpf :" + cpf + " não encontrado!");
        }
        return new DadosDeCliente(cliente.get());
    }
}
