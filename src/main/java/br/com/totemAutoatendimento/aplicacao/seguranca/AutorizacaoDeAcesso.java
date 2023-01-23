package br.com.totemAutoatendimento.aplicacao.seguranca;

import java.util.List;

import br.com.totemAutoatendimento.dominio.exception.UsuarioSemPermissaoException;
import br.com.totemAutoatendimento.dominio.usuario.Perfil;
import br.com.totemAutoatendimento.dominio.usuario.TipoPerfil;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class AutorizacaoDeAcesso {

	public static void requerirPerfilAdministrador(Usuario usuario) {
		List<Perfil> perfis = usuario.getPerfis();
		if (!perfis.contains(new Perfil(TipoPerfil.ADMINISTRADOR))) {
			throw new UsuarioSemPermissaoException(
					String.format("Usuário com registro %S sem autorização de acesso!", usuario.getRegistro()));
		}
	}

	public static void requerirPerfilFuncionario(Usuario usuario) {
		List<Perfil> perfis = usuario.getPerfis();
		if (!perfis.contains(new Perfil(TipoPerfil.FUNCIONARIO))) {
			throw new UsuarioSemPermissaoException(
					String.format("Usuário com registro %S sem autorização de acesso!", usuario.getRegistro()));
		}
	}
	
	public static void requerirPerfilTotem(Usuario usuario) {
		List<Perfil> perfis = usuario.getPerfis();
		if (!perfis.contains(new Perfil(TipoPerfil.TOTEM))) {
			throw new UsuarioSemPermissaoException(
					String.format("Usuário com registro %S sem autorização de acesso!", usuario.getRegistro()));
		}
	}


	public static void requerirPerfilAdministradorOuFuncionario(Usuario usuario) {
		List<Perfil> perfis = usuario.getPerfis();
		if (!perfis.contains(new Perfil(TipoPerfil.ADMINISTRADOR))
				&& !perfis.contains(new Perfil(TipoPerfil.FUNCIONARIO))) {
			throw new UsuarioSemPermissaoException(
					String.format("Usuário com registro %S sem autorização de acesso!", usuario.getRegistro()));
		}
	}
	
	public static void requerirQualquerPerfil(Usuario usuario) {
		List<Perfil> perfis = usuario.getPerfis();
		if (!perfis.contains(new Perfil(TipoPerfil.ADMINISTRADOR)) 
				&& !perfis.contains(new Perfil(TipoPerfil.FUNCIONARIO))
				&& !perfis.contains(new Perfil(TipoPerfil.TOTEM))) {
			throw new UsuarioSemPermissaoException(
					String.format("Usuário com registro %S sem autorização de acesso!", usuario.getRegistro()));
		}
	}
}
