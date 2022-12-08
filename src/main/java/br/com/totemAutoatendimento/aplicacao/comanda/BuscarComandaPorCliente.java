package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.totemAutoatendimento.aplicacao.pessoa.cliente.BuscarCliente;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;

public class BuscarComandaPorCliente {

    private ComandaRepository repository;

    private ClienteRepository clienteRepository;

    public BuscarComandaPorCliente(ComandaRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    public Page<DadosDeComanda> executar(Pageable paginacao, Long id) {
        BuscarCliente buscarCliente = new BuscarCliente(clienteRepository);
        Cliente cliente = buscarCliente.executar(id);
        return repository.buscarPorCliente(paginacao, cliente).map(DadosDeComanda::new);
    }
}
