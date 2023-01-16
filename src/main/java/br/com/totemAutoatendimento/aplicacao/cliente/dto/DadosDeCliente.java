package br.com.totemAutoatendimento.aplicacao.cliente.dto;

import br.com.totemAutoatendimento.dominio.cliente.Cliente;
import br.com.totemAutoatendimento.dominio.cliente.Estado;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class DadosDeCliente {

	private Long id;

	private String nome;

	private String cpf;

	private String telefone;

	private String email;

	private Estado estado;

	private String cidade;

	private String bairro;

	private String rua;

	private String numero;
	
	public DadosDeCliente(Cliente cliente) {
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.cpf = cliente.getCpf();
		this.telefone = cliente.getTelefone();
		this.email = cliente.getEmail().getEndereco();
		this.estado = cliente.getEndereco().getEstado();
		this.cidade = cliente.getEndereco().getCidade();
		this.bairro = cliente.getEndereco().getBairro();
		this.rua = cliente.getEndereco().getRua();
		this.numero = cliente.getEndereco().getNumero();
		
	}
}
