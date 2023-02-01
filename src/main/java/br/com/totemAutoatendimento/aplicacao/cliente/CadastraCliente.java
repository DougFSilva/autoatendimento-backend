package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosCriarCliente;
import br.com.totemAutoatendimento.aplicacao.logger.StandardLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CadastraCliente {

	private final ClienteRepository repository;

	private final StandardLogger logger;

	public CadastraCliente(ClienteRepository repository, StandardLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}

	@Transactional
	public Cliente cadastrar(DadosCriarCliente dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirPerfilAdministradorOuFuncionario(usuarioAutenticado);
		;
		if (repository.buscarClientePorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(
					String.format("Cliente com cpf %s já cadastrado!", dados.cpf()));
		}
		Endereco endereco = new Endereco(
				null, 
				dados.estado(), 
				dados.cidade(), 
				dados.bairro(), 
				dados.rua(),
				dados.numero());
		Email email = new Email(dados.email());
		Cliente cliente = new Cliente(dados.nome(), dados.cpf(), dados.telefone(), email, endereco);
		Cliente clienteCadastrado = repository.salvar(cliente);
		logger.info(String.format("Cliente com cpf %s cadastrado!", clienteCadastrado.getCpf()), usuarioAutenticado);
		return clienteCadastrado;
	}
}
