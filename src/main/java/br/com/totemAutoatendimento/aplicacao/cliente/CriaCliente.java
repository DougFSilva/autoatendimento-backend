package br.com.totemAutoatendimento.aplicacao.cliente;

import org.springframework.transaction.annotation.Transactional;

import br.com.totemAutoatendimento.aplicacao.cliente.dto.DadosCriarCliente;
import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.Endereco;
import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class CriaCliente {

	private final ClienteRepository repository;
	
	private final SystemLogger logger;

	public CriaCliente(ClienteRepository repository, SystemLogger logger) {
		this.repository = repository;
		this.logger = logger;
	}
	
	@Transactional
	public Cliente criar(DadosCriarCliente dados, Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);;
		if(repository.buscarClientePorCpf(dados.cpf()).isPresent()) {
			throw new ViolacaoDeIntegridadeDeDadosException(String.format("Cliente com cpf %s já cadastrado!", dados.cpf()));
		}
		Endereco endereco = new Endereco(null, dados.estado(), dados.cidade(), dados.bairro(), dados.rua(), dados.numero());
		Email email = new Email(dados.email());
		Cliente cliente = new Cliente(dados.nome(), dados.cpf(), dados.telefone(), email, endereco);
		Cliente clienteCriado = repository.criar(cliente);
		logger.info(
				String.format("Usuário %s - Cliente com cpf %s criado!", usuarioAutenticado.getRegistro(), clienteCriado.getCpf())
		);
		return clienteCriado;
	}
}
