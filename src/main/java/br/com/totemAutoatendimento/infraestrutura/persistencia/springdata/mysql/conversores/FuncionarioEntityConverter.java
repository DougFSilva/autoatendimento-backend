package br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.totemAutoatendimento.dominio.Email;
import br.com.totemAutoatendimento.dominio.funcionario.Funcionario;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.EmailEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.FuncionarioEntity;
import br.com.totemAutoatendimento.infraestrutura.persistencia.springdata.mysql.entities.UsuarioEntity;

@Service
public class FuncionarioEntityConverter {

	@Autowired 
	private UsuarioEntityConverter usuarioEntityConverter;

	public Funcionario converterParaFuncionario(FuncionarioEntity entity) {
		Usuario usuario = usuarioEntityConverter.converterParaUsuario(entity.getUsuario());
		return new Funcionario(
				entity.getId(), 
				entity.getMatricula(), 
				entity.getNome(), 
				entity.getCpf(), 
				new Email(entity.getEmail().getEndereco()),
				usuario);
	}
	
	public FuncionarioEntity converterParaFuncionarioEntity(Funcionario funcionario) {
		UsuarioEntity usuarioEntity = usuarioEntityConverter.converterParaUsuarioEntity(funcionario.getUsuario());
		return new FuncionarioEntity(
				funcionario.getId(), 
				funcionario.getMatricula(), 
				funcionario.getNome(), 
				funcionario.getCpf(), 
				new EmailEntity(funcionario.getEmail().getEndereco()),
				usuarioEntity);
				
	}
}
