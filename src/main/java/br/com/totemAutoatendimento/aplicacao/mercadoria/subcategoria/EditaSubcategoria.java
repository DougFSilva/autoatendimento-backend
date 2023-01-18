package br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria;

import java.util.Optional;

import br.com.totemAutoatendimento.aplicacao.logger.SystemLogger;
import br.com.totemAutoatendimento.aplicacao.mercadoria.categoria.BuscaCategoriaPeloId;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosDeSubcategoria;
import br.com.totemAutoatendimento.aplicacao.mercadoria.subcategoria.dto.DadosEditarSubcategoria;
import br.com.totemAutoatendimento.aplicacao.seguranca.AutorizacaoDeAcesso;
import br.com.totemAutoatendimento.dominio.exception.ViolacaoDeIntegridadeDeDadosException;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.Categoria;
import br.com.totemAutoatendimento.dominio.mercadoria.categoria.CategoriaRepository;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.Subcategoria;
import br.com.totemAutoatendimento.dominio.mercadoria.subcategoria.SubcategoriaRepository;
import br.com.totemAutoatendimento.dominio.usuario.Usuario;

public class EditaSubcategoria {

	private final SubcategoriaRepository repository;

	private final CategoriaRepository categoriaRepository;
	
	private final SystemLogger logger;

	public EditaSubcategoria(SubcategoriaRepository repository, CategoriaRepository categoriaRepository, SystemLogger logger) {
		this.repository = repository;
		this.categoriaRepository = categoriaRepository;
		this.logger = logger;
	}

    public DadosDeSubcategoria editar(Long id, DadosEditarSubcategoria dados, Usuario usuarioAutenticado) {
    	AutorizacaoDeAcesso.requerirPerfilAdministrador(usuarioAutenticado);
        BuscaSubcategoriaPeloId buscarSubcategoriaPeloId = new BuscaSubcategoriaPeloId(repository);
        Subcategoria subcategoria = buscarSubcategoriaPeloId.buscar(id);
        Optional<Subcategoria> subcategoriaPorNome = repository.buscarPeloNome(dados.nome());
        if (subcategoriaPorNome.isPresent() && subcategoriaPorNome.get().getId() != id) {
        	throw new ViolacaoDeIntegridadeDeDadosException(String.format("Subcategoria com nome %s já cadastrada!", dados.nome()));
        }
        BuscaCategoriaPeloId buscaCategoriaPeloId = new BuscaCategoriaPeloId(categoriaRepository);
        Categoria categoria = buscaCategoriaPeloId.buscar(dados.categoriaId());
        subcategoria.setNome(dados.nome());
        subcategoria.setCategoria(categoria);
        Subcategoria subcategoriaEditada = repository.editar(subcategoria);
        logger.info(
        		String.format("Usuário %s - Subcategoria de id %d editada!", 
        				usuarioAutenticado.getRegistro(), subcategoriaEditada.getId())
        );
        return new DadosDeSubcategoria(subcategoriaEditada);
    }
}
