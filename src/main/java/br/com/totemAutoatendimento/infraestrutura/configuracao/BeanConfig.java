package br.com.totemAutoatendimento.infraestrutura.configuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import br.com.totemAutoatendimento.dominio.pessoa.cliente.ClienteRepository;
import br.com.totemAutoatendimento.dominio.pessoa.usuario.UsuarioRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.cliente.ClienteEntityRepository;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.pessoa.usuario.UsuarioEntityRepository;

@Configuration
public class BeanConfig {
	
	@Autowired
	public UsuarioEntityRepository usuarioEntityRepository;
	
	@Autowired
	public ClienteEntityRepository clienteEntityRepository;

	public UsuarioRepository usuarioRepository() {
		return this.usuarioEntityRepository;
	}
	
	public ClienteRepository clienteRepository() {
		return this.clienteEntityRepository;
	}
}
