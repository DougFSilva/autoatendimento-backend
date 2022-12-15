package br.com.totemAutoatendimento.aplicacao.comanda;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import br.com.totemAutoatendimento.aplicacao.cliente.BuscarCliente;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.comanda.ComandaRepository;

public class BuscarComandaPorCliente {

    private ComandaRepository repository;

    private ClienteRepository clienteRepository;

    public BuscarComandaPorCliente(ComandaRepository repository, ClienteRepository clienteRepository) {
        this.repository = repository;
        this.clienteRepository = clienteRepository;
    }

    @PreAuthorize("hasAnyRole('FUNCIONARIO','ADMIN')")
    public Page<DadosDeComanda> executar(Pageable paginacao, Long id) {
        BuscarCliente buscarCliente = new BuscarCliente(clienteRepository);
        Cliente cliente = buscarCliente.executar(id);
        return repository.buscarPorCliente(paginacao, cliente).map(DadosDeComanda::new);
    }
}
