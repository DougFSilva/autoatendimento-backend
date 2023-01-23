package br.com.totemAutoatendimento.aplicacao.mercadoria.categoria;

import java.util.List;

import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class BuscaTodasCategorias {

	private final CategoriaRepository repository;

	public BuscaTodasCategorias(CategoriaRepository repository) {
		this.repository = repository;
	}

	public List<Categoria> buscar(Usuario usuarioAutenticado) {
		AutorizacaoDeAcesso.requerirQualquerPerfil(usuarioAutenticado);
		return repository.buscarTodas();
	}
}
