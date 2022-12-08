package br.com.totemAutoatendimento.aplicacao.pessoa.cliente;

import java.util.Optional;

import br.com.totemAutoatendimento.dominio.exception.ObjetoNaoEncontradoException;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarClientePorCpf {

    private ClienteRepository repository;

    public BuscarClientePorCpf(ClienteRepository repository) {
        this.repository = repository;
    }

    public DadosDeCliente executar(String cpf) {
        Optional<Cliente> cliente = repository.buscarClientePorCpf(cpf);
        if (cliente.isEmpty()) {
            throw new ObjetoNaoEncontradoException("Cliente com cpf :" + cpf + " não encontrado!");
        }
        return new DadosDeCliente(cliente.get());
    }
}
